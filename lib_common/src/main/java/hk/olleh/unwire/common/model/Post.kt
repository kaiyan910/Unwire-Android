package hk.olleh.unwire.common.model

import android.os.Parcelable
import hk.olleh.unwire.common.Constant
import hk.olleh.unwire.common.findFirstMatchPattern
import hk.olleh.unwire.common.timeAgo
import hk.olleh.unwire.common.toDateTime
import hk.olleh.unwire.database.entity.BookmarkEntity
import hk.olleh.unwire.network.model.PostResponse
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
data class Post(
    val id: Long,
    val title: String,
    val content: String,
    val date: String,
    val dateAgo: String,
    val slug: String,
    val link: String,
    val banner: String?,
    val excerpt: String,
    val categories: List<Long>,
    val tags: List<Long>,
    val isVideo: Boolean,
    val video: String?
) : Parcelable {

    companion object {

        fun fromDatabase(db: BookmarkEntity): Post = Post(
            id = db.id,
            title = db.title,
            content = db.content,
            date = DateTime(db.date).toString(Constant.DATETIME_FORMAT),
            dateAgo = db.date.timeAgo(),
            slug = db.slug,
            link = db.link,
            banner = db.banner,
            excerpt = db.excerpt,
            categories = db.categories
                .split(",")
                .map { it.toLong() },
            tags = if (db.tags.isEmpty()) {
                listOf()
            } else {
                db.tags
                    .split(",")
                    .map { it.toLong() }
            },
            isVideo = db.isVideo,
            video = db.video
        )

        fun fromApi(res: PostResponse): Post {

            val isVideoPost = res
                .categories
                .contains(Constant.CATEGORY_TV.toLong())

            val image = if (isVideoPost) {

                res
                    .content
                    .rendered
                    .findFirstMatchPattern(Constant.HTML_IFRAME_REGEX)
                    ?.findFirstMatchPattern(Constant.VIDEO_REGEX)
                    // try to get the thumbnail first
                    ?.let { "https://graph.facebook.com/$it/picture" }
                    // if fail use first image pattern
                    ?: res.content.rendered.findFirstMatchPattern(Constant.HTML_IMG_REGEX)
                    // else use second pattern
                    ?: res.content.rendered.findFirstMatchPattern(Constant.HTML_IMG_REGEX_2)

            } else {

                // first pattern <img />
                res.content.rendered.findFirstMatchPattern(Constant.HTML_IMG_REGEX)
                    // second pattern <img>
                    ?: res.content.rendered.findFirstMatchPattern(Constant.HTML_IMG_REGEX_2)
            }

            return Post(
                id = res.id,
                title = res.title.rendered,
                content = res
                    .content
                    .rendered
                    .replace("width=\"[0-9]+\"".toRegex(), "width=\"100%\""),
                date = res
                    .date
                    .replace("T", " "),
                dateAgo = res
                    .date
                    .replace("T", " ")
                    .toDateTime(Constant.DATETIME_FORMAT)
                    ?.millis
                    .timeAgo(),
                slug = res.slug,
                link = res.link,
                banner = image,
                excerpt = res.excerpt.rendered,
                categories = res.categories,
                tags = res.tags,
                isVideo = isVideoPost,
                video = res
                    .content
                    .rendered
                    .findFirstMatchPattern(Constant.HTML_IFRAME_REGEX)
            )
        }
    }

    fun toEntity(): BookmarkEntity = BookmarkEntity(
        id = id,
        title = title,
        content = content,
        date = date.toDateTime(Constant.DATETIME_FORMAT)?.millis,
        slug = slug,
        link = link,
        banner = banner,
        excerpt = excerpt,
        categories = categories.joinToString(","),
        tags = tags.joinToString(","),
        isVideo = isVideo,
        video = video
    )
}