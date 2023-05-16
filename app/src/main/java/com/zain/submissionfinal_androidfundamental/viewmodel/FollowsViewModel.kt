package com.zain.submissionfinal_androidfundamental.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zain.submissionfinal_androidfundamental.network.ApiConfig
import com.zain.submissionfinal_androidfundamental.response.FollowersResponseItem
import com.zain.submissionfinal_androidfundamental.response.FollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowsViewModel : ViewModel() {

    private val _followingUser = MutableLiveData<List<FollowingResponseItem>>()
    var followingUser: LiveData<List<FollowingResponseItem>> = _followingUser

    private val _followersUser = MutableLiveData<List<FollowersResponseItem>>()
    var followersUser: LiveData<List<FollowersResponseItem>> = _followersUser

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun getDataFollowing(param :String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(param)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    if(response.body() != null){
                        _followingUser.postValue(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getDataFollowers(param: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(param)
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    if(response.body() != null){
                        _followersUser.postValue(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }

        })
    }
}