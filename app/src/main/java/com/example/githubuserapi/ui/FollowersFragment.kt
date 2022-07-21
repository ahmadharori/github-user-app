package com.example.githubuserapi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.adapter.ListUserAdapter
import com.example.githubuserapi.databinding.FragmentFollowersBinding
import com.example.githubuserapi.response.UserItem
import com.example.githubuserapi.view.UserDetailViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val uname = arguments?.getString(UNAME)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        if (index == 1){
            if (uname != null) {
                viewModel.queryFollowers(uname)
            }
        } else {
            if (uname != null) {
                viewModel.queryFollowing(uname)
            }
        }

        viewModel.listFollowing.observe(viewLifecycleOwner){
            setListFollowers(it)
        }

        viewModel.listFollowers.observe(viewLifecycleOwner){
            setListFollowers(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }

    private fun setListFollowers(listFollowers: List<UserItem>) {
        Log.e("FollowersFragment", "${listFollowers.size}")
        val adapter = ListUserAdapter(listFollowers)
        binding.rvFollow.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                val userDataIntent = Intent(context, UserScrollingActivity::class.java)
                userDataIntent.putExtra(UserScrollingActivity.UNAME, data.login)
                startActivity(userDataIntent)
            }
        })
    }

    private fun showLoading(isLoading : Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val UNAME = "uname"
        const val ARG_SECTION_NUMBER = "section_number"
    }

}