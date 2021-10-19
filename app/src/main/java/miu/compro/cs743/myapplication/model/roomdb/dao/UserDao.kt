package miu.compro.cs743.myapplication.model.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import miu.compro.cs743.myapplication.model.roomdb.entity.User

@Dao
interface UserDao {
    @Query( "SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE email LIKE :email LIMIT 1")
    suspend fun findByEmail(email: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: User): List<Long>

    @Delete
    suspend fun delete(user: User)
}