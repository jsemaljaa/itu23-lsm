// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itu.lsm.R
import com.itu.lsm.databinding.FragmentLocationSelectionBinding
import androidx.navigation.fragment.findNavController
import com.itu.lsm.ui.home.SharedReservationViewModel

class LocationSelectionFragment : Fragment() {

    private var _binding: FragmentLocationSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedViewModel: SharedReservationViewModel = ViewModelProvider(requireActivity()).get(SharedReservationViewModel::class.java)

        setupSpinner()
        binding.spinnerLocations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = parent.getItemAtPosition(position).toString()
                sharedViewModel.selectLocation(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.btnConfirmLocation.setOnClickListener {
            navigateToDateTimeSelection()
        }
    }

    private fun navigateToDateTimeSelection() {
        findNavController().navigate(R.id.action_locationSelection_to_dateTimeSelection)
    }

    private fun setupSpinner() {
        // setup for a spinner with hardcoded location options
        val locations = listOf("Online", "Brno", "Prague", "Olomouc", "Ostrava")
        val adapter = ArrayAdapter(requireContext(), R.layout.location_spinner_item, locations)
        binding.spinnerLocations.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}