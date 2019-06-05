package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.common.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetBookmarkPostUseCaseImpl(
    private val databaseRepository: DatabaseRepository
) : GetBookmarkPostUseCase {

    override suspend fun getBookmarkPost(page: Int, keyword: String): List<Post> =
            withContext(Dispatchers.IO) {
                databaseRepository.getBookmark(page, 10, keyword)
            }
}