package com.zain.submissionfinal_androidfundamental.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.submissionfinal_androidfundamental.R
import com.zain.submissionfinal_androidfundamental.adapter.UserAdapter
import com.zain.submissionfinal_androidfundamental.databinding.ActivityMainBinding
import com.zain.submissionfinal_androidfundamental.response.ItemsUser
import com.zain.submissionfinal_androidfundamental.setting.SettingActivity
import com.zain.submissionfinal_androidfundamental.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainVm : MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
        binding.rvUser.setHasFixedSize(true)

        mainVm.isLoading.observe(this){
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        mainVm.listUser.observe(this){
            setData(it)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting ->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.fav->{
                val intent = Intent(this,FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mainVm.getUser(query)
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                showLoading(true)
                searchView.clearFocus()
                searchView.setQuery("", false)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    private fun setData(userAdapter: List<ItemsUser>){
        val adapter = UserAdapter(userAdapter)
        binding.rvUser.adapter = adapter
        binding.rvUser.visibility = View.VISIBLE
        adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsUser) {
                showSelectedCharacter(data)
            }
        })
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSelectedCharacter(user: ItemsUser){
        Toast.makeText(this, "Kamu memilih " + user.login, Toast.LENGTH_SHORT).show()
    }
}