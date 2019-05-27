package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse

class RetrofitRemoteDataSource(
    private val api: UnwireApi
) : RemoteDataSource {

    override suspend fun getPosts(page: Int): List<PostResponse> =
        api
            .getPosts(page)
            .await()

    override suspend fun getPostsByCategory(category: Int, page: Int): List<PostResponse> =
        api
            .getPostsByCategory(category, page)
            .await()
}