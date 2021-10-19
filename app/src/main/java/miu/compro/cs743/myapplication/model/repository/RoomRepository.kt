package miu.compro.cs743.myapplication.model.repository

import androidx.lifecycle.LiveData
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.roomdb.entity.User

interface RoomRepository {
    /**
     * USER
     */
    suspend fun getAllUsers() : List<User>
    suspend fun insertUser(user: User): List<Long>
    suspend fun findByEmail(email: String): User

    /**
     * BOOKMARK
     */
    suspend fun getAllBookmark(): List<Article>
    suspend fun insertBookmark(article: Article) : List<Long>
    suspend fun deleteAllBookmark() : Int?
//    suspend fun deleteBookmark(article: Article) : Int?
    suspend fun deleteBookmark(titleBookmark: String): Int?
}