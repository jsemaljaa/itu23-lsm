// Author: Alina Vinogradova (xvinog00)


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
import com.itu.lsm.classes.Service

class HomeViewModel : ViewModel() {

    private val database = Firebase.database

    private val tasksRef = database.getReference("tasks")
    private val servicesRef = database.getReference("services")

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _servicesInspiration = MutableLiveData<List<Service>>()
    val servicesInspiration: LiveData<List<Service>> = _servicesInspiration


    private val _servicesPopular = MutableLiveData<List<Service>>()
    val servicesPopular: LiveData<List<Service>> = _servicesPopular

    init {
        loadTasks()
        loadServices()
    }

    private fun loadTasks() {
        tasksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val taskList = mutableListOf<Task>()
                dataSnapshot.children.mapNotNullTo(taskList) { it.getValue<Task>(Task::class.java) }
                _tasks.value = taskList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun loadServices() {
        servicesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val inspList = mutableListOf<Service>()
                val popList = mutableListOf<Service>()

                dataSnapshot.children.forEach{
                    val service = it.getValue<Service>(Service::class.java)
                    when (it.key) {
                        "1", "2", "3" -> {
                            if (service != null) inspList.add(service)
                        }
                        "4", "5", "6" -> {
                            if (service != null) popList.add(service)
                        }
                    }
                }

                _servicesInspiration.value = inspList
                _servicesPopular.value = popList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadServices:onCancelled", databaseError.toException())
            }
        })
    }
}