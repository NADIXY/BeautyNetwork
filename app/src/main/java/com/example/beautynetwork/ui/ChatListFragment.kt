package com.example.beautynetwork.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.adapter.ChatUserAdapter
import com.example.beautynetwork.data.model.user.chat.ChatProfile
import com.example.beautynetwork.databinding.FragmentChatListBinding

class ChatListFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentChatListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backOnClickListener()
        setupChatList()

        binding.account.setOnClickListener {
            findNavController().navigate(R.id.chatProfileFragment)
        }

    }

    private fun setupChatList() {
        viewModel.profileCollectionReference?.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val userList = value.map { it.toObject(ChatProfile::class.java) }.toMutableList()
                userList.removeAll { it.userId == viewModel.user.value!!.uid }
                binding.rvUsers.adapter = ChatUserAdapter(userList, viewModel)
            }
        }
    }
    private fun backOnClickListener() {
        binding.textBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}