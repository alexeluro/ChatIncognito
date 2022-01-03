package com.inspiredcoda.chatincognito.presentation.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.inspiredcoda.chatincognito.R
import com.inspiredcoda.chatincognito.databinding.ChatLayoutBinding
import com.inspiredcoda.chatincognito.domain.model.ChatMessage
import com.inspiredcoda.chatincognito.utils.toDate

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val messages = mutableListOf(
        ChatMessage(
            sender = "Lisa",
            message = "Hello Bro",
            fromMe = false,
            timestamp = System.currentTimeMillis()
        ),
        ChatMessage(
            sender = "Lisa",
            message = "Whats up?",
            fromMe = false,
            timestamp = System.currentTimeMillis()
        ),
        ChatMessage(
            sender = "Lisa",
            message = "How're you doing?",
            fromMe = false,
            timestamp = System.currentTimeMillis()
        ),
        ChatMessage(
            sender = "Lisa",
            message = "Are you there?",
            fromMe = false,
            timestamp = System.currentTimeMillis()
        ),
        ChatMessage(
            sender = "Alex",
            message = "My name is Alex, not Lisa",
            fromMe = true,
            timestamp = System.currentTimeMillis()
        ),
    )

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].fromMe) MessageSender.ME else MessageSender.OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val root =
            inflater.inflate(R.layout.chat_layout, parent, false)

        val binding = ChatLayoutBinding.bind(root)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        if (messages.isNotEmpty()) {
            messages[position]?.let { chat ->
                if (chat.fromMe) {
                    holder.messageContainerLeft.visibility = View.GONE
                    holder.myName.text = chat.sender
                    holder.myMsg.text = chat.message
                    holder.myMsgDate.text = chat.timestamp.toDate()
                } else {
                    holder.messageContainerRight.visibility = View.GONE
                    holder.sender.text = chat.sender
                    holder.message.text = chat.message
                    holder.otherMessageDate.text = chat.timestamp.toDate()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun sendMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemChanged(messages.size - 1)
    }

    fun allMessages() = messages.size

    inner class ChatViewHolder(binding: ChatLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val messageContainerLeft: ConstraintLayout = binding.otherMsg
        val messageContainerRight: ConstraintLayout = binding.myMsg

        val sender: TextView = binding.senderNameLeft
        val message: TextView = binding.senderMessageLeft
        val otherMessageDate: TextView = binding.senderMessageTimeLeft

        val myName: TextView = binding.senderNameRight
        val myMsg: TextView = binding.senderMessageRight
        val myMsgDate: TextView = binding.senderMessageTimeRight
    }

    private object MessageSender {
        const val ME = 1
        const val OTHER = 0
    }
}