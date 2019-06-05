package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post

interface GetBookmarkPostUseCase {

    suspend fun getBookmarkPost(page: Int, keyword: String): List<Post>
}