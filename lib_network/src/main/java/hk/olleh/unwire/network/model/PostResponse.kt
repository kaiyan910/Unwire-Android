package hk.olleh.unwire.network.model

import com.squareup.moshi.Json

data class PostResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "date") val date: String,
    @Json(name = "slug") val slug: String,
    @Json(name = "link") val link: String,
    @Json(name = "title") val title: PostRenderedResponse,
    @Json(name = "content") val content: PostRenderedResponse,
    @Json(name = "excerpt") val excerpt: PostRenderedResponse,
    @Json(name = "categories") val categories: List<Long>,
    @Json(name = "tags") val tags: List<Long>
)

data class PostRenderedResponse(
    @Json(name = "rendered") val rendered: String
)