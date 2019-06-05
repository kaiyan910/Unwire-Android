package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.common.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPostBySlugUseCaseImpl(
    private val remoteRepository: RemoteRepository
) : GetPostBySlugUseCase {

    override suspend fun getPostBySlug(slug: String): Post? =
        withContext(Dispatchers.IO) {
            remoteRepository.getPostBySlug(slug)
        }
}