package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnwireApi {

    companion object {
        const val PREFIX = "/wp-json/wp/v2"
    }

    @GET("$PREFIX/posts")
    suspend fun getPosts(@Query("page") page: Int): List<PostResponse>

    @GET("$PREFIX/posts")
    suspend fun getPostsByCategory(
        @Query("page") page: Int,
        @Query("categories") category: Int
    ): List<PostResponse>
}