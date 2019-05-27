package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post

interface Repository {

    suspend fun getPosts(page: Int): List<Post>
    suspend fun getPostsByCategory(category: Int, page: Int): List<Post>
}