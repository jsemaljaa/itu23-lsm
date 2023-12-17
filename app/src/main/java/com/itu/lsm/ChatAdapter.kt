package com.itu.lsm
import android.util.Log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.databinding.ItemChatBinding
import com.itu.lsm.classes.Chat
import android.view.View

class ChatAdapter(private var chats: List<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(chat: Chat, position: Int)
    }

    private var showCheckBox = false
    private var selectedChatIds = HashSet<String>()
    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun submitList(newChats: List<Chat>) {
        chats = newChats
        notifyDataSetChanged()
    }

    class ChatViewHolder(private val binding: ItemChatBinding, private val listener: OnItemClickListener?, private val selectedChatIds: HashSet<String>) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat, showCheckBox: Boolean) {
            binding.chatNameTextView.text = chat.name
            binding.chatMessagePreviewTextView.text = chat.messagePreview
            binding.chatTimestampTextView.text = chat.timestamp

            if (chat.unreadCount > 0) {
                binding.chatUnreadCountTextView.text = chat.unreadCount.toString()
                binding.chatUnreadCountTextView.visibility = View.VISIBLE
            } else {
                binding.chatUnreadCountTextView.visibility = View.GONE
            }

            binding.checkBox.visibility = if (showCheckBox) View.VISIBLE else View.GONE
            binding.checkBox.isChecked = selectedChatIds.contains(chat.id)

            itemView.setOnClickListener {
                if (showCheckBox) {
                    val isChecked = binding.checkBox.isChecked
                    binding.checkBox.isChecked = !isChecked
                    if (binding.checkBox.isChecked) {
                        selectedChatIds.add(chat.id)
                        Log.d("ChatAdapter", "Selected chat with ID: ${chat.id}")
                    } else {
                        selectedChatIds.remove(chat.id)
                        Log.d("ChatAdapter", "Deselected chat with ID: ${chat.id}")
                    }
                }
                listener?.onItemClick(chat, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding, itemClickListener, selectedChatIds)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chats[position]
        holder.bind(chat, showCheckBox)
    }

    override fun getItemCount() = chats.size

    fun showCheckBoxes(show: Boolean) {
        showCheckBox = show
        notifyDataSetChanged()
    }

    fun getSelectedChatIds(): HashSet<String> {
        return selectedChatIds
    }

    fun clearSelectedChatIds() {
        selectedChatIds.clear()
    }
}
