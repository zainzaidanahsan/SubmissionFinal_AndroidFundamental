package com.zain.submissionfinal_androidfundamental.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zain.submissionfinal_androidfundamental.network.ApiConfig
import com.zain.submissionfinal_androidfundamental.response.DetailUserResponse
import com.zain.submissionfinal_androidfundamental.room.database.Favorite
import com.zain.submissionfinal_androidfundamental.room.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val repository = FavoriteRepository(application)

    private val _user = MutableLiveData<DetailUserResponse>()
    var user: LiveData<DetailUserResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading : LiveData<Boolean> = _isLoading

    private val _favorite = MutableLiveData<Favorite>()
    var favorite : LiveData<Favorite> = _favorite

    fun addData(data: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addFavorite(data)
        }
    }
    fun deleteData(data: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavorite(data)
        }
    }

    fun favoriteExist(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val favorite = repository.getFavoriteByUsername(username)
            withContext(Dispatchers.Main) {
                _favorite.value = favorite
            }
        }
    }

    fun getDetailUser(param: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(param)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful){
                    _user.value = response.body()
                }else{
                    Log.e("Error", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Err", "onFailure: ${t.message}")
            }

        })
    }
}