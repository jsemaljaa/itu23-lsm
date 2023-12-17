// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm.ui.tasks

import TaskBigAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.itu.lsm.R
import com.itu.lsm.classes.Task
import com.itu.lsm.databinding.FragmentTasksBinding


class TasksFragment : Fragment(), TaskBigAdapter.OnTaskClickListener {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var tasksViewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksViewModel = ViewModelProvider(this).get(TasksViewModel::class.java)

        binding.rvTasks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val taskBigAdapter = TaskBigAdapter(emptyList(), { task ->
            showConfirmationDialog(task)
        }, { task ->
            // Handle contact click
        }).apply {
            taskClickListener = this@TasksFragment
        }

        binding.rvTasks.adapter = taskBigAdapter

        tasksViewModel.tasks.observe(viewLifecycleOwner) {tasks ->
            taskBigAdapter.updateTasks(tasks)
        }
    }

    override fun onCardClicked(task: Task) {
        // Start TaskDetailsFragment and pass the task to it
        val action = TasksFragmentDirections.actionNavigationTasksToTaskDetailsFragment(task)
        findNavController().navigate(action)
    }


    private fun showConfirmationDialog(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm Cancellation")
        builder.setMessage("Are you sure you want to cancel ${task.title}?")

        builder.setPositiveButton("Yes") { dialog, which ->
            deleteTask(task)
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun deleteTask(task: Task) {
        val database = Firebase.database
        val tasksRef = database.getReference("tasks")

        tasksRef.child(task.id).removeValue()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}