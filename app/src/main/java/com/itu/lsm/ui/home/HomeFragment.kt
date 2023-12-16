package com.itu.lsm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.TaskAdapter
import com.itu.lsm.databinding.FragmentHomeBinding

import androidx.lifecycle.ViewModelProvider
import com.itu.lsm.ServiceAdapter
import com.itu.lsm.classes.Task

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    // ViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Processing Upcoming tasks //
        binding.rvTasks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val ttasks: MutableList<Task> = mutableListOf()
        ttasks.clear()
        val taskAdapter = TaskAdapter(ttasks)
        binding.rvTasks.adapter = taskAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTasks)

        homeViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTasks(tasks)
            setupTabDots(minOf(tasks.size, 3))
        }


        // Processing Inspiration for you section //
        binding.rvInspiration.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val serviceAdapterInsp = ServiceAdapter(emptyList())
        binding.rvInspiration.adapter = serviceAdapterInsp

        PagerSnapHelper().attachToRecyclerView(binding.rvInspiration)

        homeViewModel.servicesInspiration.observe(viewLifecycleOwner) { services ->
            serviceAdapterInsp.updateServices(services)
        }

        // Processing Popular in your area section //
        binding.rvPopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val serviceAdapterPop = ServiceAdapter(emptyList())
        binding.rvPopular.adapter = serviceAdapterPop

        PagerSnapHelper().attachToRecyclerView(binding.rvPopular)

        homeViewModel.servicesPopular.observe(viewLifecycleOwner) { services ->
            serviceAdapterPop.updateServices(services)
        }

        setupRecyclerViewScrollListener()
    }

    private fun setupTabDots(itemCount: Int) {
        val tabLayout = binding.tabDots
        tabLayout.removeAllTabs()
        repeat(itemCount) {
            tabLayout.addTab(tabLayout.newTab())
        }
        for (i in 0 until tabLayout.tabCount) {
            val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(5, 0, 5, 0)
            tab.requestLayout()
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
