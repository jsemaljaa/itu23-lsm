// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.itu.lsm.R
import com.itu.lsm.databinding.FragmentServiceDetailsBinding
import com.itu.lsm.classes.Service

class ServiceDetailsFragment : Fragment() {

    private var _binding: FragmentServiceDetailsBinding? = null
    private val binding get() = _binding!!

    // Assuming Service class has title, price, and description fields
    private var service: Service? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve and display service details
        service = arguments?.getParcelable("service")

        displayServiceDetails()

        // Set up button click listeners
        binding.btnMakeReservation.setOnClickListener {
            navigateToLocationSelection()
        }

        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun displayServiceDetails() {
        service?.let { svc ->
            binding.tvServiceTitle.text = svc.title
            binding.tvServicePrice.text = svc.price
            binding.tvServiceDescription.text = svc.description
        }
    }

    private fun navigateToLocationSelection() {
        // Replace 'action_serviceDetails_to_locationSelection' with the actual action ID
        findNavController().navigate(R.id.action_serviceDetails_to_locationSelection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
