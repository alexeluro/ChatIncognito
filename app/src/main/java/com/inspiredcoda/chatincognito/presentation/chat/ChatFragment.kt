package com.inspiredcoda.chatincognito.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inspiredcoda.chatincognito.databinding.FragmentChatBinding
import com.inspiredcoda.chatincognito.domain.model.ChatMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = _binding!!

    private lateinit var chatAdapter: ChatAdapter

    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter()

        initRecyclerView()

        binding.sendBtn.setOnClickListener {
            if (validateInputs()) {
                chatAdapter.sendMessage(
                    ChatMessage(
                        sender = "Alex",
                        message = binding.chatInput.text.toString(),
                        fromMe = true,
                        timestamp = System.currentTimeMillis()
                    )
                )
                binding.chatInput.setText("")
                binding.chatRecyclerView.scrollToPosition(chatAdapter.allMessages() - 1)
            }
        }

        binding.toolbar.apply {
            backBtn.setOnClickListener {
                navController.popBackStack()
            }

            toolbarTitle.text = "Therapy Lounge"
        }

        observer()
    }

    private fun initRecyclerView() {
        binding.chatRecyclerView.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(ChatItemDecoration())
        }
    }

    private fun observer() {

    }

    private fun validateInputs(): Boolean {
        if (binding.chatInput.text.isNullOrBlank()) {
            return false
        }
        return true
    }

}