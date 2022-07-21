package com.example.githubuserapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.adapter.FavoriteUserAdapter
import com.example.githubuserapi.database.FavoriteUser
import com.example.githubuserapi.databinding.ActivityFavoriteUserBinding
import com.example.githubuserapi.settings.SettingPreferences
import com.example.githubuserapi.view.FavoriteUserViewModel
import com.example.githubuserapi.view.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding :ActivityFavoriteUserBinding

    private lateinit var favoriteUserAdapter : FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        val favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllFavoriteUser().observe(this) {
            if (it != null){
                favoriteUserAdapter.setListFavoriteUser(it)
            }
        }

        favoriteUserAdapter = FavoriteUserAdapter(favoriteUserViewModel,
            this@FavoriteUserActivity)
        favoriteUserAdapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUser) {
                val userDataIntent = Intent(this@FavoriteUserActivity,
                    UserScrollingActivity::class.java)
                userDataIntent.putExtra(UserScrollingActivity.UNAME, data.username)
                startActivity(userDataIntent)
            }
        })

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            setHasFixedSize(true)
            adapter = favoriteUserAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val search = menu.findItem(R.id.search)
        search.isVisible = false

        val favorite = menu.findItem(R.id.favorites)
        favorite.isVisible = false

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings ->{
                val i = Intent(this, DarkModeActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteUserViewModel {
        val pref = SettingPreferences.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(pref, activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }
}