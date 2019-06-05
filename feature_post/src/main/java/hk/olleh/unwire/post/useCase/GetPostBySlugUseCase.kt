package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post

interface GetPostBySlugUseCase {

    suspend fun getPostBySlug(slug: String): Post?
}