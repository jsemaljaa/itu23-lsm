// Author: Anastasiia Berezovska

package com.itu.lsm.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.R
import com.itu.lsm.ReservationsAdapter
import com.itu.lsm.classes.Reservation
import com.itu.lsm.classes.Service
import com.itu.lsm.databinding.FragmentReservationsHistoryBinding

class ReservationsHistoryFragment : Fragment() {
    private var _binding: FragmentReservationsHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var reservationsAdapter: ReservationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationsHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerViewReservations
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // todo later replace this with list of reservations
        val reservations = getDummyReservations()

        reservationsAdapter = ReservationsAdapter(reservations) { position ->
            // Handle the "Remove" button click for the item at the specified position
            reservationsAdapter.notifyItemRemoved(position)
        }

        recyclerView.adapter = reservationsAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDummyReservations(): List<Reservation> {
        // todo later replace this with list of reservations
        return listOf(
            Reservation("1", Service("Tutoring", "..."), "17/12/22", "18:00", "Study Session", "Ostrava"),
            Reservation("2", Service("Cleaning", "..."), "16/12/22", "9:00", "Cleaning", "Prague"),
            Reservation("3", Service("Training", "..."), "15/12/22", "11:00", "Training", "Brno"),
            Reservation("4", Service("Counselling", "..."), "17/12/22", "16:00", "Counselling", "Prague")
        )
    }

}

