package com.example.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.database.FavoriteUser
import com.example.githubuserapi.databinding.FavoriteUserRowBinding
import com.example.githubuserapi.helper.FavoriteUserDiffCallback
import com.example.githubuserapi.ui.FavoriteUserActivity
import com.example.githubuserapi.view.FavoriteUserViewModel

class FavoriteUserAdapter(
    favoriteUserViewModel: FavoriteUserViewModel,
    favoriteUserActivity: FavoriteUserActivity
): RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    private val listFavoriteUser = ArrayList<FavoriteUser>()
    private val favoriteUserViewModel = favoriteUserViewModel
    private val favoriteUserActivity = favoriteUserActivity

    fun setListFavoriteUser(listFavoriteUsers: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUser, listFavoriteUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUser.clear()
        this.listFavoriteUser.addAll(listFavoriteUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = FavoriteUserRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavoriteUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listFavoriteUser[holder.adapterPosition]
            )
        }
    }

    override fun getItemCount(): Int {
        return listFavoriteUser.size
    }

    inner class FavoriteUserViewHolder(
        private val binding: FavoriteUserRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            Glide.with(itemView.context)
                .load(favoriteUser.avatar_url)
                .circleCrop()
                .into(binding.imgItemAvatar)
            binding.tvItemName.text = favoriteUser.username
            binding.deleteButton.setOnClickListener {
                favoriteUserViewModel.deleteUserFromFavoriteUser(favoriteUser)
                Toast.makeText(
                    favoriteUserActivity,
                    "${favoriteUser.username} has been deleted from Favorite User",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }
}