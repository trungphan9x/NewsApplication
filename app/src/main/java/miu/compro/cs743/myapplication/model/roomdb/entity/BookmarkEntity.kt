package miu.compro.cs743.myapplication.model.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import miu.compro.cs743.myapplication.model.remote.response.Source
@Entity(tableName = "bookmark")
data class BookmarkEntity(
    @PrimaryKey
    val title: String,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    var bookmarkedAt: Long = System.currentTimeMillis()
)