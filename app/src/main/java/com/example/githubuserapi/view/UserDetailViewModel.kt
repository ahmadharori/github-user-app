package com.example.githubuserapi.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.api.ApiConfig
import com.example.githubuserapi.response.UserItem
import com.example.githubuserapi.response.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _user = MutableLiveData<UserDetailResponse>()
    val user: LiveData<UserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<UserItem>>()
    val listFollowers: LiveData<List<UserItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<UserItem>>()
    val listFollowing: LiveData<List<UserItem>> = _listFollowing


    fun queryDetail(uname: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(uname)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun queryFollowers(uname: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(uname)
        client.enqueue(object : Callback<List<UserItem>> {

            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun queryFollowing(uname: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(uname)
        client.enqueue(object : Callback<List<UserItem>> {

            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "UserDetailViewModel"
    }

}