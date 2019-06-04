package hk.olleh.unwire.database

import hk.olleh.unwire.database.entity.BookmarkEntity

interface DatabaseDataSource {

    suspend fun getBookmark(page: Int, limit: Int): List<BookmarkEntity>
    suspend fun delete(remoteId: Long)
    suspend fun insert(entity: BookmarkEntity)
    suspend fun isBookmarked(id: Long): Boolean
}