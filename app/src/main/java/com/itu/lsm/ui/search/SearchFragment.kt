package com.itu.lsm.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itu.lsm.R
import com.itu.lsm.ServiceBigAdapter
import com.itu.lsm.classes.Service
import com.itu.lsm.databinding.FragmentSearchBinding
import com.itu.lsm.ui.home.SharedReservationViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var sharedViewModel: SharedReservationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedReservationViewModel::class.java)

        val serviceAdapter = ServiceBigAdapter(emptyList()) { service ->
            sharedViewModel.selectService(service)
            navigateToServiceDetails(service)
        }
        binding.rvSearchResults.layoutManager = LinearLayoutManager(context)
        binding.rvSearchResults.adapter = serviceAdapter

        searchViewModel.fetchServices()

        searchViewModel.services.observe(viewLifecycleOwner) { services ->
            if (services != null) {
                serviceAdapter.updateServices(services)
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchViewModel.searchServices(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchViewModel.searchServices(it) }
                return false
            }
        })
    }

    private fun navigateToServiceDetails(service: Service) {
        val action = SearchFragmentDirections.actionNavigationSearchToServiceDetailsFragment(service)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
