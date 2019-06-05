package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.common.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookmarkPostUseCaseImpl(
    private val databaseRepository: DatabaseRepository
) : BookmarkPostUseCase {

    override suspend fun bookmark(post: Post, isAdd: Boolean) =
        withContext(Dispatchers.IO) {
            if (isAdd) {
                databaseRepository.insert(post)
            } else {
                databaseRepository.delete(post.id)
            }
        }
}