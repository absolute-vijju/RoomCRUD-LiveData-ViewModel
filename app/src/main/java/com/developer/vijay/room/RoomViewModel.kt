package com.developer.vijay.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.vijay.room.database.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(private val repository: Repo) : ViewModel() {
    fun getUsers() = repository.getUsers()

    fun getFavouriteUsers() = repository.getFavouriteUsers()

    fun insertUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.insertUser(userEntity)
    }

    fun updateUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.updateUser(userEntity)
    }

    fun addRemoveFavourite(userId: Long, isFavourite: Boolean) = viewModelScope.launch {
        repository.addRemoveFavourite(userId, isFavourite)
    }

    fun deleteUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.deleteUser(userEntity)
    }
}