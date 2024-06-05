package com.example.beautynetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.user.chat.ChatProfile
import com.example.beautynetwork.databinding.ItemChatUserBinding

class ChatUserAdapter(
    private val dataset: List<ChatProfile>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<ChatUserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemChatUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemChatUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userItem = dataset[position]

        holder.binding.tvUsername.text = userItem.username
        /*holder.binding.imageView2.load(userItem.profilePictureChat){
        error(R.drawable.baseline_account_box_24)
        }
         */

        //val imageUrl = ""
        //holder.binding.imageView2.load(imageUrl)

        holder.binding.cvUser.setOnClickListener {
            viewModel.setCurrentChat(userItem.userId)
            holder.itemView.findNavController().navigate(R.id.chatFragment)
        }

    }
}