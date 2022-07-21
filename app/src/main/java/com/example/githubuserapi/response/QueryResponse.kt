package com.example.githubuserapi.response

import com.google.gson.annotations.SerializedName

data class QueryResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val items: List<UserItem>
)

data class UserItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String
)
