package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse

interface RemoteDataSource {

    suspend fun getPosts(page: Int = 0, isPro: Boolean = false): List<PostResponse>

    suspend fun getPostBySlug(slug: String): PostResponse?

    suspend fun getPostsByCategory(
        category: String,
        page: Int = 0,
        isPro: Boolean = false,
        search: String = ""
    ): List<PostResponse>
}