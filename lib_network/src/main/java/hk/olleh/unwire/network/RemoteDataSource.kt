package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse

interface RemoteDataSource {

    suspend fun getPosts(page: Int = 0): List<PostResponse>
    suspend fun getPostsByCategory(category: Int, page: Int = 0): List<PostResponse>
}