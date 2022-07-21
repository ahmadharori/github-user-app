package com.example.githubuserapi.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.api.ApiConfig
import com.example.githubuserapi.event.Event
import com.example.githubuserapi.response.UserItem
import com.example.githubuserapi.response.QueryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _queryCount = MutableLiveData<Event<Int>>()
    val queryCount : LiveData<Event<Int>> = _queryCount

    private val _listUser = MutableLiveData<List<UserItem>>()
    val listUser : LiveData<List<UserItem>> =_listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun queryUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getQueryUser(query)
        client.enqueue(object : Callback<QueryResponse> {
            override fun onResponse(
                call: Call<QueryResponse>,
                response: Response<QueryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                    _queryCount.value = response.body()?.let { Event(it.totalCount) }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<QueryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}