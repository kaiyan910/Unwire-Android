package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.network.RemoteDataSource

class RetrofitRemoteRepository(
    private val remoteDataSource: RemoteDataSource
) : RemoteRepository {

    override suspend fun getPosts(
        page: Int,
        isPro: Boolean
    ): List<Post> = remoteDataSource
        .getPosts(page, isPro)
        .map { Post.fromApi(it) }

    override suspend fun getPostsByCategory(
        category: String,
        page: Int,
        isPro: Boolean,
        search: String
    ): List<Post> = remoteDataSource
        .getPostsByCategory(category, page, isPro, search)
        .map { Post.fromApi(it) }
}