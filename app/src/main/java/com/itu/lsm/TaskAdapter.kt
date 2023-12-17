// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.databinding.ItemTaskCardSmallBinding
import com.itu.lsm.classes.Task

class TaskAdapter(private var tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTaskCardSmallBinding.bind(view)
        val tvTaskDate: TextView = binding.tvTaskDate
        val tvTaskDescription: TextView = binding.tvTaskDescription

        fun bind(task: Task) {
            tvTaskDate.text = task.date + " " + task.time
            tvTaskDescription.text = task.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_card_small, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount() = minOf(tasks.size, 3)

    fun updateTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks.take(3))
        notifyDataSetChanged()
    }
}
