package com.zain.submissionfinal_androidfundamental.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zain.submissionfinal_androidfundamental.R
import com.zain.submissionfinal_androidfundamental.adapter.SectionPager
import com.zain.submissionfinal_androidfundamental.databinding.ActivityDetailBinding
import com.zain.submissionfinal_androidfundamental.response.DetailUserResponse
import com.zain.submissionfinal_androidfundamental.room.database.Favorite
import com.zain.submissionfinal_androidfundamental.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val KEY_USER = "key_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followingText,
            R.string.followerText
        )
    }
    private var isFavorite: Boolean? = null
        private set
    private lateinit var favorite: Favorite
    private lateinit var binding : ActivityDetailBinding
    private val detailVM : DetailViewModel by lazy {
        ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userLogin = intent.getStringExtra(KEY_USER)

        val viewPager : ViewPager2 = binding.viewPager
        val sectionsPagerAdapter = SectionPager(this, userLogin?: "")

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabLay

        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailVM.isLoading.observe(this){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        if (userLogin != null) {
            detailVM.getDetailUser(userLogin)
        }
        detailVM.user.observe(this) { user ->
            getDetailUser(user)
            favorite = Favorite(user.id, user.avatarUrl, user.login)
            detailVM.favoriteExist(user.login)
        }

        setIsFavorite(true)
        var favoriteStatus = checkIsFavorite()
        binding.btnFav.setOnClickListener {
            if (favoriteStatus) {
                detailVM.addData(favorite)
                changeFavoriteValue(false)
                favoriteStatus = false
            } else {
                detailVM.deleteData(favorite)
                changeFavoriteValue(true)
                favoriteStatus = true
            }
        }
        detailVM.user.observe(this){ value ->
            val boolean = value != null
            changeFavoriteValue(boolean)
        }

    }
    fun checkIsFavorite(): Boolean {
        return isFavorite ?: throw IllegalStateException("belum ada nilai pada isFavorite")
    }

    private fun setIsFavorite(value: Boolean) {
        isFavorite = value
    }

    private fun changeFavoriteValue(favoriteValue: Boolean) {
        val resId = if (favoriteValue) {
            R.drawable.ic_baseline_favorite_border_24
        } else {
            R.drawable.ic_baseline_favorite_24
        }
        binding.btnFav.setImageResource(resId)
        setIsFavorite(favoriteValue)
        isFavorite = favoriteValue
    }

    fun getDetailUser(user: DetailUserResponse){
        Glide.with(this).load(user.avatarUrl).into(binding.picProfil)
        val newFollowingValue = user.following.toString()
        val newFollowerValue = user.followers.toString()
        binding.apply {
            txtUsername.text = user.login
            txtName.text = user.name
            txtFollowers.text = StringBuilder().append(newFollowerValue).append(" ").append("Followers")
            txtFollowing.text = StringBuilder().append(newFollowingValue).append(" ").append("Following")
        }

    }
}