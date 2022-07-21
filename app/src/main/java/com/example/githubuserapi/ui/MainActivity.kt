package com.example.githubuserapi.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.adapter.ListUserAdapter
import com.example.githubuserapi.databinding.ActivityMainBinding
import com.example.githubuserapi.response.UserItem
import com.example.githubuserapi.settings.SettingPreferences
import com.example.githubuserapi.view.DarkModeViewModel
import com.example.githubuserapi.view.MainViewModel
import com.example.githubuserapi.view.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Search User"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        viewModel.queryUser("a")

        viewModel.listUser.observe(this){
            setListUser(it)
        }

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.queryCount.observe(this){
            it.getContentIfNotHandled().let { queryCount ->
                if (queryCount == 0) {
                    Toast.makeText(
                        this@MainActivity, "No result found", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val darkModeViewModel = ViewModelProvider(this,
            ViewModelFactory(pref, this.application)
        )[DarkModeViewModel::class.java]

        darkModeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.queryUser(query)
                }
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites -> {
                val i = Intent(this, FavoriteUserActivity::class.java)
                startActivity(i)
                true
            }
            R.id.settings ->{
                val i = Intent(this, DarkModeActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }


    private fun setListUser(listQuery: List<UserItem>){
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
        val adapter = ListUserAdapter(listQuery)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val userDataIntent =Intent(this@MainActivity,
                    UserScrollingActivity::class.java)
                userDataIntent.putExtra(UserScrollingActivity.UNAME, data.login)
                startActivity(userDataIntent)
            }
        })
    }

    private fun showLoading(isLoading : Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}