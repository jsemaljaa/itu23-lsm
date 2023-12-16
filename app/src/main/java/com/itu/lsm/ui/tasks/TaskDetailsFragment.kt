package com.itu.lsm.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itu.lsm.classes.Task
import com.itu.lsm.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

    // Use companion object to create new instances of this fragment
    companion object {
        private const val ARG_TASK = "task"

        fun newInstance(task: Task): TaskDetailsFragment {
            val args = Bundle()
            args.putSerializable(ARG_TASK, task)
            val fragment = TaskDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = arguments?.getSerializable(ARG_TASK) as Task?

        task?.let {
            // If you have an image, you can set it to your ImageView here
            // binding.ivDetailImage.setImageResource(...)
            binding.tvDetailTitle.text = it.title
            binding.tvDetailLocation.text = it.loc
            binding.tvDetailTime.text = it.time + " " + it.date
        }

        binding.btnDetailBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}