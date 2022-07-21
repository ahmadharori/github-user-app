package com.example.githubuserapi.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapi.ui.FollowersFragment

class SectionsPagerAdapter(activity: AppCompatActivity, uname: String)
    : FragmentStateAdapter(activity) {

    private val username = uname

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = FollowersFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowersFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowersFragment.UNAME, username)
        }
        return fragment
    }


}