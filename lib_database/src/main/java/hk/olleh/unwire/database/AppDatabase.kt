package hk.olleh.unwire.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hk.olleh.unwire.database.dao.BookmarkDao
import hk.olleh.unwire.database.entity.BookmarkEntity

@Database(
    entities = [BookmarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao
}