package miu.compro.cs743.myapplication.model.roomdb.dao

import androidx.room.*
import miu.compro.cs743.myapplication.model.roomdb.entity.BookmarkEntity

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    suspend fun getAllBookmark(): List<BookmarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBookmark(vararg bookmarkEntity: BookmarkEntity): List<Long>

    @Query("DELETE FROM bookmark WHERE title = :titleBookmark")
    suspend fun deleteBookmark(titleBookmark: String) : Int?

    @Query("DELETE FROM bookmark")
    suspend fun deleteAllBookmark() : Int?
}