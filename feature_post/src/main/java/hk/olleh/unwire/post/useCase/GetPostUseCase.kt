package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.base.BaseUseCase
import hk.olleh.unwire.common.model.Post

interface GetPostUseCase : BaseUseCase {
    suspend fun getPosts(category: String, page: Int = 0): List<Post>
}