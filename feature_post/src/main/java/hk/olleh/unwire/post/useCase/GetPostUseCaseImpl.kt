package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.common.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPostUseCaseImpl(
    private val repository: RemoteRepository
) : GetPostUseCase {

    override suspend fun getPosts(category: String, page: Int, isPro: Boolean): List<Post> =
        withContext(Dispatchers.IO) {
            repository.getPostsByCategory(category, page, isPro)
        }
}