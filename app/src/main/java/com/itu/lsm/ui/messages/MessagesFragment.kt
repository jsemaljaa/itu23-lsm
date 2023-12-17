package com.itu.lsm.ui.messages

import android.os.Bundle
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.itu.lsm.databinding.FragmentMessagesBinding
import com.itu.lsm.classes.Chat
import com.itu.lsm.ChatAdapter

class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatAdapter: ChatAdapter
    private val chats: MutableList<Chat> = mutableListOf()

    private var isEditMode = false
    private val selectedChatIds = HashSet<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonClose.setOnClickListener { onCloseButtonClick() }
        binding.buttonEdit.setOnClickListener { onEditButtonClick() }
        binding.buttonDelete.setOnClickListener { onDeleteButtonClick() }

        // Dummy data for chats, similar to what you've used in your MainActivity
        chats.addAll(
            listOf(
                Chat(id = "1", name = "Alice Smith", messagePreview = "Hi there! How are you?", timestamp = "10:42 AM", unreadCount = 2),
                Chat(id = "2", name = "Bob Johnson", messagePreview = "Don't forget about the meeting.", timestamp = "Yesterday", unreadCount = 1),
                Chat(id = "3", name = "Charlie Brown", messagePreview = "Can you send me the report?", timestamp = "2 days ago", unreadCount = 0)
                // ... more chats
            )
        )

        chatAdapter = ChatAdapter(chats)

        binding.recyclerView.apply {
            // Use a linear layout manager
            layoutManager = LinearLayoutManager(context)
            // Specify an adapter
            adapter = chatAdapter
        }

        chatAdapter.setOnItemClickListener(object : ChatAdapter.OnItemClickListener {
            override fun onItemClick(chat: Chat, position: Int) {

                if (isEditMode) {
                    val chatId = chat.id
                    val isChecked = !selectedChatIds.contains(chatId)

                    if (isChecked) {
                        selectedChatIds.add(chatId)
                    } else {
                        selectedChatIds.remove(chatId)
                    }
                    chatAdapter.notifyDataSetChanged()
                } else {
                    // Handle normal chat item click (e.g., open chat)
                }
            }
        })

        // Add a TextWatcher to the search EditText
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter chats based on the search query
                filterChats(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed
            }
        })
    }

    private fun onEditButtonClick() {
        isEditMode = true
        binding.buttonEdit.visibility = View.GONE
        binding.buttonDelete.visibility = View.VISIBLE
        binding.buttonClose.visibility = View.VISIBLE  // Show the close button
        chatAdapter.showCheckBoxes(true)
    }


    private fun onDeleteButtonClick() {
        Log.d("MessagesFragment", "Delete button clicked, selectedChatIds: $selectedChatIds")

        // Ensure there's something to delete
        if (selectedChatIds.isNotEmpty()) {
            val iterator = chats.iterator()
            while (iterator.hasNext()) {
                val chat = iterator.next()
                if (selectedChatIds.contains(chat.id)) {
                    Log.d("MessagesFragment", "Deleting chat with id: ${chat.id}")
                    iterator.remove()
                }
            }

            // Notify the adapter of the changes
            chatAdapter.notifyDataSetChanged() // Use this if your adapter is not a ListAdapter

            // Clear the selection
            selectedChatIds.clear()

            // Exit edit mode and update UI
            exitEditMode()
        } else {
            Log.d("MessagesFragment", "No chats selected for deletion.")
            exitEditMode() // Even if no chats are selected, you may want to exit edit mode
        }
    }

    private fun exitEditMode() {
        isEditMode = false
        binding.buttonEdit.visibility = View.VISIBLE
        binding.buttonDelete.visibility = View.GONE
        binding.buttonClose.visibility = View.GONE
        chatAdapter.showCheckBoxes(false)
        chatAdapter.clearSelectedChatIds() // Correct method name
        chatAdapter.notifyDataSetChanged()
    }

    private fun onCloseButtonClick() {
        isEditMode = false
        binding.buttonEdit.visibility = View.VISIBLE
        binding.buttonDelete.visibility = View.GONE
        binding.buttonClose.visibility = View.GONE
        chatAdapter.showCheckBoxes(false)
        chatAdapter.getSelectedChatIds().clear()
        chatAdapter.notifyDataSetChanged()

    }


    private fun filterChats(query: String) {
        val filteredList = if (query.isEmpty()) {
            // If the query is empty, show all chats
            chats
        } else {
            // Filter chats based on the query
            chats.filter { chat ->
                chat.name.contains(query, ignoreCase = true) ||
                        chat.messagePreview.contains(query, ignoreCase = true)
            }
        }

        chatAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
