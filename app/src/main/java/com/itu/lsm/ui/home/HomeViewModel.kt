package com.itu.lsm.ui.home

import android.util.Log
import androidx.fragment.app.FragmentManager.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.itu.lsm.classes.Task

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    private val database = Firebase.database
    private val tasksRef = database.getReference("tasks")
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        tasksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val taskList = mutableListOf<Task>()
//                for (snapshot in dataSnapshot.children) {
//                    val task = snapshot.getValue(Task::class.java)
//                    task?.let { taskList.add(it) }
//                }
                dataSnapshot.children.mapNotNullTo(taskList) { it.getValue<Task>(Task::class.java) }
                _tasks.value = taskList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}