package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post

interface DatabaseRepository {

    suspend fun getBookmark(page: Int, limit: Int): List<Post>
    suspend fun insert(post: Post)
    suspend fun delete(id: Long)
    suspend fun isBookmarked(id: Long): Boolean
}