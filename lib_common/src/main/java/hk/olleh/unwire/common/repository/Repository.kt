package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post

interface Repository {

    suspend fun getPosts(page: Int, isPro: Boolean): List<Post>
    suspend fun getPostsByCategory(category: String, page: Int, isPro: Boolean = false): List<Post>
}