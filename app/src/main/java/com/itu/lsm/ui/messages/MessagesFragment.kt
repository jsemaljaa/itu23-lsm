// Author: Maryia Mazurava xmazur08

package com.itu.lsm.ui.messages

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Populate the list only once when the fragment is created
        populateDummyChats()
    }

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


        chatAdapter = ChatAdapter(chats).apply {
            setOnItemClickListener(object : ChatAdapter.OnItemClickListener {
                override fun onItemClick(chat: Chat, position: Int) {
                    if (isEditMode) {
                        toggleChatSelection(chat.id)
                    } else {
                        // Handle normal chat item click (e.g., open chat)
                    }
                }
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }

        setupSearchEditText()
    }

    private fun populateDummyChats() {
        chats.addAll(
            listOf(
                Chat(id = "1", name = "Alice Smith", messagePreview = "Hi there! How are you?", timestamp = "10:42 AM", unreadCount = 2),
                Chat(id = "2", name = "Bob Johnson", messagePreview = "Don't forget about the meeting.", timestamp = "Yesterday", unreadCount = 1),
                Chat(id = "3", name = "Charlie Brown", messagePreview = "Can you send me the report?", timestamp = "2 days ago", unreadCount = 0)
            )
        )
    }

    private fun toggleChatSelection(chatId: String) {
        val isSelected = selectedChatIds.contains(chatId)
        if (isSelected) {
            selectedChatIds.remove(chatId)
        } else {
            selectedChatIds.add(chatId)
        }
        chatAdapter.setSelectedChatIds(selectedChatIds)
        chatAdapter.notifyDataSetChanged()
    }

    private fun setupSearchEditText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterChats(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun onEditButtonClick() {
        isEditMode = true
        binding.buttonEdit.visibility = View.GONE
        binding.buttonDelete.visibility = View.VISIBLE
        binding.buttonClose.visibility = View.VISIBLE
        chatAdapter.showCheckBoxes(true)
    }

    private fun onDeleteButtonClick() {
        if (selectedChatIds.isNotEmpty()) {
            chats.removeAll { chat -> selectedChatIds.contains(chat.id) }
            chatAdapter.submitList(ArrayList(chats)) // Update the adapter's data set.
            selectedChatIds.clear() // Clear the selection.
            exitEditMode() // Exit edit mode.
        }
    }

    private fun exitEditMode() {
        isEditMode = false
        binding.buttonEdit.visibility = View.VISIBLE
        binding.buttonDelete.visibility = View.GONE
        binding.buttonClose.visibility = View.GONE
        chatAdapter.showCheckBoxes(false)
        chatAdapter.clearSelectedChatIds()
        chatAdapter.notifyDataSetChanged() // Refresh the list.
    }

    private fun onCloseButtonClick() {
        exitEditMode()
    }

    private fun filterChats(query: String) {
        val filteredList = if (query.isEmpty()) chats else chats.filter {
            it.name.contains(query, ignoreCase = true) || it.messagePreview.contains(query, ignoreCase = true)
        }
        chatAdapter.submitList(ArrayList(filteredList)) // Pass a copy of the filtered list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = MessagesFragment::class.java.simpleName
    }
}
