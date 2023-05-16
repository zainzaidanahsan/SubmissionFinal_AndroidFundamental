package com.zain.submissionfinal_androidfundamental.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zain.submissionfinal_androidfundamental.room.database.Favorite
import com.zain.submissionfinal_androidfundamental.room.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoriteViewModel(application: Application):AndroidViewModel(application) {
    private val repository = FavoriteRepository(application)

    private val _listFavorite = MutableLiveData<List<Favorite>>()
    var favorite : LiveData<List<Favorite>> = _listFavorite

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading : LiveData<Boolean> = _isLoading

    fun getFavoriteUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllFavorite()
            withContext(Dispatchers.Main) {
                _listFavorite.value = response
            }
        }
    }

    fun deleteData(data: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavorite(data)
            withContext(Dispatchers.Main) {
                getFavoriteUser()
            }
        }
    }
}