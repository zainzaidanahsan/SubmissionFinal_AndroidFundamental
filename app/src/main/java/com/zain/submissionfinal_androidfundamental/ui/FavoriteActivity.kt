package com.zain.submissionfinal_androidfundamental.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.submissionfinal_androidfundamental.R
import com.zain.submissionfinal_androidfundamental.adapter.FavoriteAdapter
import com.zain.submissionfinal_androidfundamental.databinding.ActivityFavoriteBinding
import com.zain.submissionfinal_androidfundamental.room.database.Favorite
import com.zain.submissionfinal_androidfundamental.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteVm : FavoriteViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[FavoriteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteVm.isLoading.observe(this){
            binding.progressbarFav.visibility = if (it) View.VISIBLE else View.GONE
        }
        favoriteVm.favorite.observe(this) {
            if(it.isEmpty()) {
                binding.rvFavorite.isVisible = false
                binding.txtFavKosong.isVisible = true
            } else {
                binding.rvFavorite.isVisible = true
                binding.txtFavKosong.isVisible = false
                setAdapter(it)
            }
        }
    }
    private fun setAdapter(data: List<Favorite>) {
        val adapter = FavoriteAdapter(data)
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            this.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favorite) {
                favoriteVm.deleteData(data)
            }
        })
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbarFav.visibility = View.VISIBLE
        } else {
            binding.progressbarFav.visibility = View.GONE
        }
    }

    override fun onPause() {
        favoriteVm.getFavoriteUser()
        showLoading(true)
        favoriteVm.favorite.observe(this) {
            showLoading(false)
            if(it.isEmpty()) {
                binding.rvFavorite.isVisible = false
                binding.txtFavKosong.isVisible = true
            } else {
                binding.rvFavorite.isVisible = true
                binding.txtFavKosong.isVisible = false
                setAdapter(it)
            }
        }
        super.onPause()
    }

    override fun onResume() {
        favoriteVm.getFavoriteUser()
        showLoading(true)
        favoriteVm.favorite.observe(this) {
            showLoading(false)
            if(it.isEmpty()) {
                binding.rvFavorite.isVisible = false
                binding.txtFavKosong.isVisible = true
            } else {
                binding.rvFavorite.isVisible = true
                binding.txtFavKosong.isVisible = false
                setAdapter(it)
            }
        }
        super.onResume()
    }
}