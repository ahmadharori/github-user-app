package com.example.githubuserapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.adapter.SectionsPagerAdapter
import com.example.githubuserapi.database.FavoriteUser
import com.example.githubuserapi.databinding.ActivityUserScrollingBinding
import com.example.githubuserapi.response.UserDetailResponse
import com.example.githubuserapi.settings.SettingPreferences
import com.example.githubuserapi.view.FavoriteUserViewModel
import com.example.githubuserapi.view.UserDetailViewModel
import com.example.githubuserapi.view.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class UserScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserScrollingBinding
    private val userDetailViewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "User Detail"

        val user = intent.getStringExtra(UNAME)
        userDetailViewModel.queryDetail(user!!)

        val sectionPagerAdapter = SectionsPagerAdapter(this, user)
        binding.include.viewPager?.adapter = sectionPagerAdapter

        userDetailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        userDetailViewModel.user.observe(this){
            setUserDetail(it)
            binding.include.viewPager?.let { viewPager2 ->
                binding.include.tabs?.let { tab ->
                    TabLayoutMediator(tab, viewPager2) { tab, position ->
                        if (position == 0){
                            tab.text = resources.getString(TAB_TITLES[position]) + "\n${it.followers}"
                        } else {
                            tab.text = resources.getString(TAB_TITLES[position]) + "\n${it.following}"
                        }
                    }.attach()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val favoriteUserViewModel = obtainViewModel(this@UserScrollingActivity)

        userDetailViewModel.user.observe(this) {
            favoriteUserViewModel.getFavoriteUserByUsername(it.login).observe(this){ listUser ->
                if (listUser.isNotEmpty()){
                    binding.fabAdd.visibility = View.GONE
                } else {
                    binding.fabAdd.visibility = View.VISIBLE
                    binding.fabAdd.setOnClickListener { view ->
                        if (view.id == R.id.fab_add) {
                            val favUser = FavoriteUser()
                            favUser.apply {
                                username = it.login
                                avatar_url = it.avatarUrl
                            }
                            favoriteUserViewModel.insertUserToFavoriteUser(favUser)
                            showToast("${favUser.username} added to Favorite User")
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.option_menu, menu)

        val search = menu.findItem(R.id.search)
        if (search != null) {
            search.isVisible = false
        }

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

    private fun setUserDetail(user : UserDetailResponse){
        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.include3.avatar)
        binding.include3.apply {
            usernameValue.text = user.login
            nameValue.text = user.name ?: "-"
            companyValue.text = user.company ?: "-"
            locationValue.text = user.location ?: "-"
            repositoryValue.text = user.publicRepos.toString()
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteUserViewModel {
        val pref = SettingPreferences.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(pref, activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading : Boolean) {
        binding.include3.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val UNAME = "uname"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}