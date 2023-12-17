package com.itu.lsm.ui.profile

import ProfileViewModel
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.itu.lsm.R
import com.itu.lsm.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var usersRef: DatabaseReference
    private val profileViewModel: ProfileViewModel by viewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        usersRef = Firebase.database.reference.child("users")

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView1: TextView = binding.nameText
        val textView2: TextView = binding.surnameText
        val textView3: TextView = binding.Username

        profileViewModel.name.observe(viewLifecycleOwner) {
            textView1.text = it
        }

        profileViewModel.surname.observe(viewLifecycleOwner) {
            textView2.text = it
        }

        profileViewModel.username.observe(viewLifecycleOwner) {
            textView3.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the TextView representing "Reservations History"
        val reservationsHistoryTextView =
            view.findViewById<TextView>(R.id.reservationsHistoryTextView)

        // Set an OnClickListener to handle the click event and navigate when it is clicked
        reservationsHistoryTextView.setOnClickListener {
            findNavController().navigate(R.id.fragment_reservations_history)
        }

        // Find the edit button in your layout
        val editButton = view.findViewById<Button>(R.id.editButton)

        // Set an OnClickListener to handle the click event
        editButton.setOnClickListener {
            showEditDialog()
        }

    }

    // Define setUsersReference function to set the reference from MainActivity
    // usersRef
    fun setUsersReference(usersRef: DatabaseReference) {
        this.usersRef = usersRef
    }

    private fun showEditDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.edit_username_dialog)

        val editTextUsername = dialog.findViewById<EditText>(R.id.editTextUsername)
        val buttonSubmit = dialog.findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val newUsername = editTextUsername.text.toString()
            profileViewModel.updateUsername(newUsername)
            dialog.dismiss()
        }

        dialog.show()
    }
}
