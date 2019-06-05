package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckPostBookmarkUseCaseImpl(
    private val databaseRepository: DatabaseRepository
) : CheckPostBookmarkUseCase {

    override suspend fun isPostBookmarked(id: Long): Boolean =
        withContext(Dispatchers.IO) {
            databaseRepository.isBookmarked(id)
        }
}