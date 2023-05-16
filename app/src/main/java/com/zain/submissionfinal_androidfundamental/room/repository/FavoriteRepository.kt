package com.zain.submissionfinal_androidfundamental.room.repository

import android.app.Application
import com.zain.submissionfinal_androidfundamental.room.database.Favorite
import com.zain.submissionfinal_androidfundamental.room.database.FavoriteDao
import com.zain.submissionfinal_androidfundamental.room.database.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoritDao()
    }

    fun addFavorite(data : Favorite){
        executorService.execute{mFavoriteDao.insert(data)}
    }

    fun getAllFavorite(): List<Favorite> {
        return mFavoriteDao.getAllFavorite()
    }

    fun getFavoriteByUsername(username: String): Favorite {
        return mFavoriteDao.getFavoriteUserByUsername(username)
    }

    fun deleteFavorite(data: Favorite) {
        executorService.execute {
            mFavoriteDao.delete(data)
        }
    }


}