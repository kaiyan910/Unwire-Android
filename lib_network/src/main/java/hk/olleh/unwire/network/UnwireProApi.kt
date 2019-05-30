package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface UnwireProApi {

    companion object {
        const val PREFIX = "/wp-json/wp/v2"
    }

    @GET("$PREFIX/posts")
    fun getPosts(@Query("page") page: Int): Deferred<List<PostResponse>>

    @GET("${UnwireApi.PREFIX}/posts")
    fun getPostsByCategory(
        @Query("categories") category: String,
        @Query("page") page: Int
    ): Deferred<List<PostResponse>>
}