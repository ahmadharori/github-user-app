package com.example.githubuserapi.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.database.FavoriteUser
import com.example.githubuserapi.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> =
        mFavoriteUserRepository.getAllFavoriteUser()

    fun insertUserToFavoriteUser(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String) : LiveData<List<FavoriteUser>> =
        mFavoriteUserRepository.getFavoriteUserByUsername(username)

    fun deleteUserFromFavoriteUser(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
}