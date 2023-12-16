package com.itu.lsm

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.itu.lsm.databinding.ActivityMainBinding
import android.widget.SearchView
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.itu.lsm.classes.Task
import java.util.UUID


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_tasks, R.id.navigation_search, R.id.navigation_messages))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        database = Firebase.database.reference
        var tasksRef = database.child("tasks")


        fun addTask(date: String, time: String, title: String) {
            val id = UUID.randomUUID().toString()

            Log.w("Debug", "Adding task")


            tasksRef.child(id).setValue(Task(date, time, title))
        }

        addTask("WED, NOV 29", "14:00", "English lesson")
        addTask("FRI, DEC 15", "11:00", "Cleaning")
        addTask("SAT, DEC 16", "09:00", "Cleaning")
    }
}