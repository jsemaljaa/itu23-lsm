// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm.ui.tasks

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.itu.lsm.R
import com.itu.lsm.classes.Task
import com.itu.lsm.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: TaskDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = args.task

        binding.tvDetailTitle.text = task.title
        binding.tvDetailLocation.text = task.loc
        binding.tvDetailTime.text = task.date + " " + task.time

        binding.btnDetailBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.btnDetailCancel.setOnClickListener {
            val taskId = task?.id

            // Show a confirmation dialog before deletion
            taskId?.let { id ->
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Confirm Cancellation")
                    setMessage("Are you sure you want to cancel this task?")
                    setPositiveButton("Yes") { dialog, which ->
                        val database = Firebase.database
                        val tasksRef = database.getReference("tasks")

                        tasksRef.child(id).removeValue()
                        parentFragmentManager.popBackStack()
                    }
                    setNegativeButton("No", null)
                    show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}