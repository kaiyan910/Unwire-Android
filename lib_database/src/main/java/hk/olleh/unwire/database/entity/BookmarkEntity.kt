package hk.olleh.unwire.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: Long?,
    @ColumnInfo(name = "slug") val slug: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "banner") val banner: String?,
    @ColumnInfo(name = "excerpt") val excerpt: String,
    @ColumnInfo(name = "categories") val categories: String,
    @ColumnInfo(name = "tags") val tags: String,
    @ColumnInfo(name = "isVideo") val isVideo: Boolean,
    @ColumnInfo(name = "video") val video: String?
)