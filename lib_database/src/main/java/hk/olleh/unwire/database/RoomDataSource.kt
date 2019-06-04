package hk.olleh.unwire.database

import hk.olleh.unwire.database.entity.BookmarkEntity

class RoomDataSource(
    private val database: AppDatabase
) : DatabaseDataSource {

    override suspend fun getBookmark(page: Int, limit: Int) = database
            .bookmarkDao()
            .getBookmarkWithPaging(page * limit, limit)

    override suspend fun delete(remoteId: Long) = database
        .bookmarkDao()
        .delete(remoteId)

    override suspend fun insert(entity: BookmarkEntity) = database
        .bookmarkDao()
        .insert(entity)

    override suspend fun isBookmarked(id: Long): Boolean = database
        .bookmarkDao()
        .isBookmarked(id) > 0
}