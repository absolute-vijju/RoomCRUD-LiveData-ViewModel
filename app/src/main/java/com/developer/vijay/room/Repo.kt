package com.developer.vijay.room

import com.developer.vijay.room.database.UserDao
import com.developer.vijay.room.database.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repo @Inject constructor(private val dao: UserDao) {
    fun getUsers() = dao.getUsers()

    fun getFavouriteUsers() = dao.getFavouriteUsers()

    suspend fun insertUser(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        dao.insertUser(userEntity)
    }

    suspend fun updateUser(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        dao.updateUser(userEntity)
    }

    suspend fun addRemoveFavourite(userId: Long, isFavourite: Boolean) = withContext(Dispatchers.IO) {
        dao.addRemoveToFavourite(userId, isFavourite)
    }

    suspend fun deleteUser(userEntity: UserEntity) = withContext(Dispatchers.IO) {
        dao.deleteUser(userEntity)
    }
}
