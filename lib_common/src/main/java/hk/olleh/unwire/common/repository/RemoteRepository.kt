package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post

interface RemoteRepository {

    suspend fun getPosts(page: Int, isPro: Boolean): List<Post>

    suspend fun getPostBySlug(slug: String): Post?

    suspend fun getPostsByCategory(
        category: String,
        page: Int,
        isPro: Boolean = false,
        search: String = ""
    ): List<Post>
}