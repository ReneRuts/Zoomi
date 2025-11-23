package com.group1.zoomi.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) = userDao.insert(user)
    suspend fun updateUser(user: User) = userDao.update(user)
    suspend fun deleteUser(user: User) = userDao.delete(user)

    fun getUser(id: Int): Flow<User> = userDao.getUser(id)
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
}