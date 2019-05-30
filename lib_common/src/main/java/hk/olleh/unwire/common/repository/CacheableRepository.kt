package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.network.RemoteDataSource

class CacheableRepository(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getPosts(page: Int, isPro: Boolean): List<Post> = remoteDataSource
        .getPosts(page, isPro)
        .map { Post.fromApi(it) }

    override suspend fun getPostsByCategory(category: String, page: Int, isPro: Boolean): List<Post> =
        remoteDataSource
            .getPostsByCategory(category, page, isPro)
            .map { Post.fromApi(it) }
}