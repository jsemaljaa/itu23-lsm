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

        // Set up the RecyclerView
        binding.rvTasks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val taskAdapter = TaskAdapter(emptyList())
        binding.rvTasks.adapter = taskAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTasks)

        binding.rvTasks.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val space = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin) // Define this in your dimens.xml
                outRect.left = space
                outRect.right = space
            }
        })

        // Observe the tasks LiveData from the ViewModel
        homeViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.updateTasks(tasks) // You'll need to implement updateTasks in your adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
