package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post

interface SearchPostUseCase {

    suspend fun getPosts(category: String, page: Int = 0, search: String): List<Post>
}