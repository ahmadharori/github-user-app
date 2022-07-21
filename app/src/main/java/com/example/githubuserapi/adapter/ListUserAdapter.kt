package com.example.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.databinding.ItemUserRowBinding
import com.example.githubuserapi.response.UserItem

class ListUserAdapter(private val listUser : List<UserItem>)
    : RecyclerView.Adapter<ListUserAdapter.ListUserHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    inner class ListUserHolder(var binding: ItemUserRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserHolder {
        val binding = ItemUserRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListUserHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemAvatar)
        holder.apply {
            binding.tvItemName.text = user.login
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(
                    listUser[holder.adapterPosition]
                )
            }
        }
    }

    override fun getItemCount() = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
    }
}