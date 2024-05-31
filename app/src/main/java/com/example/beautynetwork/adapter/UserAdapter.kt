package com.example.beautynetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.user.Profile
import com.example.beautynetwork.databinding.ItemUserBinding

class UserAdapter (
    private val dataset: List<Profile>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userItem = dataset[position]
        holder.binding.tvUsername.text = userItem.username
        holder.binding.cvUser.setOnClickListener {
            viewModel.setCurrentChat(userItem.userId)
            holder.itemView.findNavController().navigate(R.id.chatFragment)
        }
    }
}