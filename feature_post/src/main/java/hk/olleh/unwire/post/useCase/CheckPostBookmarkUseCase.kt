package hk.olleh.unwire.post.useCase

interface CheckPostBookmarkUseCase {

    suspend fun isPostBookmarked(id: Long): Boolean
}