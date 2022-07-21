package com.example.githubuserapi.api

import com.example.githubuserapi.response.UserItem
import com.example.githubuserapi.response.QueryResponse
import com.example.githubuserapi.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getQueryUser(
        @Query("q") q: String
    ): Call<QueryResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserItem>>
}