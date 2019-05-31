package hk.olleh.unwire.common.model

import android.os.Parcelable
import hk.olleh.unwire.common.Constant
import hk.olleh.unwire.common.findFirstMatchPattern
import hk.olleh.unwire.common.timeAgo
import hk.olleh.unwire.common.toDateTime
import hk.olleh.unwire.network.model.PostResponse
import kotlinx.android.parcel.Parcelize

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
                    ?.let { "https://graph.facebook.com/$it/picture" }

            } else {

                res
                    .content
                    .rendered
                    .findFirstMatchPattern(Constant.HTML_IMG_REGEX)
            }

            return Post(
                id = res.id,
                title = res.title.rendered,
                content = res.content.rendered,
                date = res
                    .date
                    .replace("T", " "),
                dateAgo = res
                    .date
                    .replace("T", " ")
                    .toDateTime("yyyy-MM-dd HH:mm:ss")
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
}