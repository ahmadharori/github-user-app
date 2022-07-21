package com.example.githubuserapi.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapi.database.FavoriteUser
import com.example.githubuserapi.database.FavoriteUserDao
import com.example.githubuserapi.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mFavoriteUserDao : FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser() : LiveData<List<FavoriteUser>> =
        mFavoriteUserDao.getAllFavoriteUser()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String) : LiveData<List<FavoriteUser>> =
        mFavoriteUserDao.getFavoriteUserByUsername(username)
}