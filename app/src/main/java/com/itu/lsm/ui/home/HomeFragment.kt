package com.itu.lsm.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.R
import com.itu.lsm.TaskAdapter
import com.itu.lsm.databinding.FragmentHomeBinding

import android.util.Log

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    // ViewModel
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTasks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val taskAdapter = TaskAdapter(emptyList())
        binding.rvTasks.adapter = taskAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTasks)

        homeViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTasks(tasks)
            setupTabDots(tasks.size)
        }
//        Log.d("HomeFragment", "Item count: $itemCount")

        setupRecyclerViewScrollListener()
    }

    private fun setupTabDots(itemCount: Int) {
        val tabLayout = binding.tabDots
        tabLayout.removeAllTabs()
        repeat(itemCount) {
            tabLayout.addTab(tabLayout.newTab())
        }
    }

    private fun setupRecyclerViewScrollListener() {
        val recyclerView = binding.rvTasks
        val tabLayout = binding.tabDots

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val currentPage = layoutManager.findFirstVisibleItemPosition()
                tabLayout.getTabAt(currentPage)?.select()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
