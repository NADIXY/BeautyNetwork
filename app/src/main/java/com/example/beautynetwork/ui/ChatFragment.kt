package com.example.beautynetwork.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.adapter.ChatAdapter
import com.example.beautynetwork.data.model.user.chat.Chat
import com.example.beautynetwork.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private lateinit var viewBinding: FragmentChatBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentChatBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonSendOnClickListener()
        setupChatMessages()
    }

    private fun setupChatMessages() {
        viewModel.currentChatDocumentReference.addSnapshotListener { value, error ->
            if (value != null && error == null) {
                val chat = value.toObject(Chat::class.java)
                val chatMessages = chat?.messages
                viewBinding.rvChatMessages.adapter =
                    chatMessages?.let { ChatAdapter(it, viewModel.user.value!!.uid) }
            }
        }
    }

    private fun setButtonSendOnClickListener() {
        viewBinding.btSend.setOnClickListener {
            val message = viewBinding.tietMessage.text.toString()
            viewModel.sendMessage(message)
        }
    }
}