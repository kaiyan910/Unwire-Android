package hk.olleh.unwire.network

import hk.olleh.unwire.network.model.PostResponse

class RetrofitRemoteDataSource(
    private val api: UnwireApi,
    private val proApi: UnwireProApi
) : RemoteDataSource {

    override suspend fun getPosts(page: Int, isPro: Boolean): List<PostResponse> = if (!isPro) {
        api.getPosts(page)
    } else {
        proApi.getPosts(page)
    }

    override suspend fun getPostBySlug(slug: String): PostResponse? =
        api
            .getPostBySlug(slug)
            .firstOrNull()

    override suspend fun getPostsByCategory(
        category: String,
        page: Int,
        isPro: Boolean,
        search: String
    ): List<PostResponse> = if (!isPro) {
        api.getPostsByCategory(category, page, search)
    } else {
        proApi.getPostsByCategory(category, page, search)
    }
}