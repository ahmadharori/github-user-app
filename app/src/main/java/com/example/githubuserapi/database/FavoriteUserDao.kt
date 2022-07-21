package com.example.githubuserapi.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from favoriteuser ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * from favoriteuser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String) : LiveData<List<FavoriteUser>>

}