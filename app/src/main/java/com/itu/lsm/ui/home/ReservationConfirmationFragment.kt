// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.itu.lsm.databinding.FragmentReservationConfirmationBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.itu.lsm.classes.Task

class ReservationConfirmationFragment : Fragment() {

    private var _binding: FragmentReservationConfirmationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedReservationViewModel::class.java)


        sharedViewModel.selectedService.observe(viewLifecycleOwner) { service ->
            binding.tvServiceTitle.text = service.title
        }

        sharedViewModel.selectedLocation.observe(viewLifecycleOwner) { location ->
            binding.tvSelectedLocation.text = location
        }

        sharedViewModel.selectedDateTime.observe(viewLifecycleOwner) { dateTime ->
            binding.tvSelectedDate.text = dateTime
        }

        binding.btnCompleteReservation.setOnClickListener {
            addNewReservation()
        }
    }

    private fun addNewReservation() {
        val tasksRef = FirebaseDatabase.getInstance().getReference("tasks")
        tasksRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Convert string to int to get new ID
                    val lastId = snapshot.children.first().key?.toIntOrNull() ?: 0
                    val newId = lastId + 1
                    saveReservation(newId)
                } else {
                    // No tasks found, start with an ID of 1
                    saveReservation(1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors here
            }
        })
    }

    private fun saveReservation(newId: Int) {
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedReservationViewModel::class.java)
        val serviceTitle = sharedViewModel.selectedService.value?.title ?: ""
        val location = sharedViewModel.selectedLocation.value ?: ""
        val dateTime = sharedViewModel.selectedDateTime.value ?: ""

        // Here you can now add the new reservation using the newId
        val tasksRef = FirebaseDatabase.getInstance().getReference("tasks")
        Log.w("datetime", dateTime)
        Log.w("time", dateTime.split(" ")[2])
        val date = dateTime.split(" ")[0] + " " + dateTime.split(" ")[1] + " " + dateTime.split(" ")[2]
        tasksRef.child(newId.toString()).setValue(Task(newId.toString(), date, dateTime.split(" ")[3], serviceTitle, location))

        val action = HomeFragmentDirections.actionReservationConfirmationToTasksPage()
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
