package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnwireProApi {

    companion object {
        const val PREFIX = "/wp-json/wp/v2"
    }

    @GET("$PREFIX/posts")
    suspend fun getPosts(@Query("page") page: Int): List<PostResponse>

    @GET("${UnwireApi.PREFIX}/posts")
    suspend fun getPostBySlug(@Query("slug") slug: String): List<PostResponse>

    @GET("${UnwireApi.PREFIX}/posts")
    suspend fun getPostsByCategory(
        @Query("categories") category: String,
        @Query("page") page: Int,
        @Query("search") search: String,
        @Query("orderby") orderBy: String = "date"
    ): List<PostResponse>
}