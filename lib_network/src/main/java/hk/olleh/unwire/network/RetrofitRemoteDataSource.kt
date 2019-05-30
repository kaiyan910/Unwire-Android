package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse

class RetrofitRemoteDataSource(
    private val api: UnwireApi,
    private val proApi: UnwireProApi
) : RemoteDataSource {


    override suspend fun getPosts(page: Int, isPro: Boolean): List<PostResponse> = if (!isPro) {

        api
            .getPosts(page)
            .await()

    } else {

        proApi
            .getPosts(page)
            .await()
    }

    override suspend fun getPostsByCategory(category: String, page: Int, isPro: Boolean): List<PostResponse> = if (!isPro) {
        api
            .getPostsByCategory(category, page)
            .await()
    } else{

        proApi
            .getPostsByCategory(category, page)
            .await()
    }
}