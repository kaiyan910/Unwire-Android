package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post

interface GetBookmarkUseCase {

    suspend fun getBookmark(page: Int, limit: Int): List<Post>
}