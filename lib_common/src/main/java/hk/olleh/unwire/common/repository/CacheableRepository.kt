package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.network.RemoteDataSource

class CacheableRepository(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getPosts(page: Int): List<Post> =
        remoteDataSource
            .getPosts(page)
            .map { Post.fromApi(it) }

    override suspend fun getPostsByCategory(category: Int, page: Int): List<Post> =
        remoteDataSource
            .getPostsByCategory(category, page)
            .map { Post.fromApi(it) }
}