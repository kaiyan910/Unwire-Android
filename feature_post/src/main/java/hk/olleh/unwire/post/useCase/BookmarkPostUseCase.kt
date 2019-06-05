package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post

interface BookmarkPostUseCase {

    suspend fun bookmark(post: Post, isAdd: Boolean)
}