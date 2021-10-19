package miu.compro.cs743.myapplication.model.repository

import androidx.lifecycle.LiveData
import miu.compro.cs743.myapplication.model.roomdb.entity.User

interface RoomRepository {
    suspend fun getAllUsers() : List<User>
    suspend fun insertUser(user: User): List<Long>
    suspend fun findByEmail(email: String): User
}