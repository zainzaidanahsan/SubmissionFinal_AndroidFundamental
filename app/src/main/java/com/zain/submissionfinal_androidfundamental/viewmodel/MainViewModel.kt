package com.zain.submissionfinal_androidfundamental.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zain.submissionfinal_androidfundamental.network.ApiConfig
import com.zain.submissionfinal_androidfundamental.response.ItemsUser
import com.zain.submissionfinal_androidfundamental.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    companion object{
        const val USER_ID = "z"
    }

    private val _listUser = MutableLiveData<List<ItemsUser>>()
    var listUser: LiveData<List<ItemsUser>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading : LiveData<Boolean> = _isLoading

    init {
        getUser(USER_ID)
    }

    fun getUser(param : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(param)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listUser.value = response.body()?.items
                }else{
                    Log.e("Error", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Err", "onFailure: ${t.message}")
            }

        })
    }
}