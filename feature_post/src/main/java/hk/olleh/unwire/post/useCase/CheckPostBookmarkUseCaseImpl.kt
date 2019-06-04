package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.repository.DatabaseRepository

class CheckPostBookmarkUseCaseImpl(
    private val databaseRepository: DatabaseRepository
) : CheckPostBookmarkUseCase {

    override suspend fun isPostBookmarked(id: Long): Boolean {



        return true
    }
}