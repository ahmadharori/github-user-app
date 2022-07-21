package com.example.githubuserapi.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("location")
	val location: String?
)
