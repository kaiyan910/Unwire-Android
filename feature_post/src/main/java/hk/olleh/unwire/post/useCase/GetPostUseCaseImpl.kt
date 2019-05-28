package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.common.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPostUseCaseImpl(
    private val repository: Repository
) : GetPostUseCase {

    override suspend fun getPosts(category: String, page: Int): List<Post> =
        withContext(Dispatchers.IO) {
            repository.getPostsByCategory(category, page)
        }
}