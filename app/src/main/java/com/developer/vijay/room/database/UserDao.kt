package com.developer.vijay.room.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface UserDao {
    @Query("SELECT * FROM tbl_users")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM tbl_users WHERE isFavourite=:isFavourite")
    fun getFavouriteUsers(isFavourite: Boolean = true): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("UPDATE tbl_users SET isFavourite=:isFavourite WHERE userId=:userId")
    suspend fun addRemoveToFavourite(userId: Long, isFavourite: Boolean)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
}