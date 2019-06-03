package hk.olleh.unwire.post.useCase

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.common.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SearchPostUseCaseImpl(
    private val cacheableRepository: Repository
) : SearchPostUseCase {

    override suspend fun getPosts(
        category: String,
        page: Int,
        search: String
    ): List<Post> = withContext(Dispatchers.IO) {

        // get Unwire's post
        val unwirePosts = async { cacheableRepository.getPostsByCategory(category, page, false, search) }
        // get Unwire Pro's post
        val unwireProPosts = async { cacheableRepository.getPostsByCategory(category, page, true, search) }

        // merge two results
        mutableListOf<Post>()
            .apply {
                addAll(unwirePosts.await())
                addAll(unwireProPosts.await())
                sortByDescending { it.date }
            }
    }
}