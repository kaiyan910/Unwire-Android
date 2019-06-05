package hk.olleh.unwire.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hk.olleh.unwire.database.entity.BookmarkEntity

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmark WHERE title LIKE '%' || :keyword || '%' OR content LIKE '%' || :keyword || '%' LIMIT :limit OFFSET :offset")
    fun getBookmarkWithPaging(offset: Int, limit: Int, keyword: String): List<BookmarkEntity>

    @Insert
    fun insert(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmark WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT COUNT(id) FROM bookmark WHERE id = :id")
    fun isBookmarked(id: Long): Int
}