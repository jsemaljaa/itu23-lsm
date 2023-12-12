package com.itu.lsm.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itu.lsm.TaskAdapter
import com.itu.lsm.databinding.FragmentTasksBinding
import com.itu.lsm.classes.Task

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTasksRecyclerView()
    }

    private fun setupTasksRecyclerView() {
        val taskList = listOf(
            Task("WED, NOV 29 • 14:00", "English lesson"),
            Task("FRI, DEC 15 • 11:00", "Cleaning"),
            // Add more tasks here
        )

        with(binding.rvTasks) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TaskAdapter(taskList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}