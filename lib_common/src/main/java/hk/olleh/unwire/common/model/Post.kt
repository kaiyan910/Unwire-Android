package hk.olleh.unwire.common.model

import hk.olleh.unwire.common.Constant
import hk.olleh.unwire.common.findFirstMatchPattern
import hk.olleh.unwire.common.toDateTime
import hk.olleh.unwire.network.model.PostResponse
import org.joda.time.DateTime

data class Post(
    val id: Long,
    val title: String,
    val content: String,
    val date: DateTime?,
    val slug: String,
    val link: String,
    val banner: String?,
    val excerpt: String,
    val categories: List<Long>,
    val tags: List<Long>
) {

    companion object {

        fun fromApi(res: PostResponse) = Post(
            id = res.id,
            title = res.title.rendered,
            content = res.content.rendered,
            date = res
                .date
                .toDateTime(Constant.API_DATETIME_FORMAT),
            slug = res.slug,
            link = res.link,
            banner = res
                .content
                .rendered
                .findFirstMatchPattern(Constant.HTML_IMG_REGEX),
            excerpt = res.excerpt.rendered,
            categories = res.categories,
            tags = res.tags
        )
    }
}