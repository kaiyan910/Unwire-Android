package hk.olleh.unwire.common.repository

import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.database.DatabaseDataSource

class RoomDatabaseRepository(
    private val dataSource: DatabaseDataSource
) : DatabaseRepository {

    override suspend fun getBookmark(page: Int, limit: Int, keyword: String): List<Post> =
        dataSource
            .getBookmark(page, limit, keyword)
            .map { Post.fromDatabase(it) }

    override suspend fun insert(post: Post) =
        dataSource
            .insert(post.toEntity())

    override suspend fun delete(id: Long) =
        dataSource
            .delete(id)

    override suspend fun isBookmarked(id: Long): Boolean =
        dataSource
            .isBookmarked(id)
}