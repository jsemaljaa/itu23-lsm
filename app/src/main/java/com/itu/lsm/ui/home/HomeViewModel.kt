package com.itu.lsm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itu.lsm.classes.Task

class HomeViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>().apply {
        value = listOf(
            Task("WED, NOV 29 • 14:00", "English lesson"),
            Task("FRI, DEC 15 • 11:00", "Cleaning"),
            Task("SAT, DEC 16 • 09:00", "Cleaning")
        )
    }

    val tasks: LiveData<List<Task>> = _tasks
}