package miu.compro.cs743.myapplication.model.repository

import androidx.lifecycle.LiveData
import miu.compro.cs743.myapplication.model.roomdb.dao.UserDao
import miu.compro.cs743.myapplication.model.roomdb.entity.User

class RoomRepositoryImpl(private val userDao: UserDao) : RoomRepository {

    override suspend fun getAllUsers() = userDao.getAll()

    override suspend fun insertUser(user: User) = userDao.insertAll(user)

    override suspend fun findByEmail(email: String) = userDao.findByEmail(email)
}