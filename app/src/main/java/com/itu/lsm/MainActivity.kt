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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.itu.lsm.classes.Reservation
import com.itu.lsm.classes.Service
import com.itu.lsm.classes.Task
import com.itu.lsm.classes.Chat
import com.itu.lsm.classes.User
import com.itu.lsm.ui.profile.ProfileFragment


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
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_tasks, R.id.navigation_messages, R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        database = Firebase.database.reference
        var tasksRef = database.child("tasks")


        fun addTask(id: String, date: String, time: String, title: String, loc: String) {
            Log.w("Debug", "Adding task")
            tasksRef.child(id).setValue(Task(id, date, time, title, loc))
        }

        addTask("1","WED, DEC 13", "14:00", "English lesson", "Online")
        addTask("2","FRI, DEC 15", "11:00", "Training", "Brno")
        addTask("3","SAT, DEC 16", "09:00", "Cleaning", "Prague")
        addTask("4","SUN, DEC 17", "16:00", "Cooking lesson", "Prague")
        addTask("5","SUN, DEC 17", "18:00", "Tutoring", "Prague")

        var servicesRef = database.child("services")

        fun addService(id: String, title: String, description: String, price: String) {
            servicesRef.child(id).setValue(Service(title, description, "", price))
        }

        addService("1", "Tutoring", "Unlock your potential with personalized guidance and achieve academic success.", "~15€/h")
        addService("2", "Cleaning", "Sparkling clean solutions for your space. Professional cleaning services that make your home or office shine.", "~10€/h")
        addService("3", "Training", "Boost your fitness journey with customized training. Personalized workouts, expert guidance.", "~25€/h")
        addService("4", "Counselling", "Navigate life's challenges with tailored counseling. Compassionate support, insightful assistance.", "~40€/h")
        addService("5", "English Lessons", "Experience engaging, interactive learning that boosts your confidence and command of the language.", "~30€/h")
        addService("6", "Cooking Lessons", "Perfect your palate and kitchen techniques in sessions designed for any skill level. Cook, create, and savor the success!", "~35€/h")

        val reservationsRef = database.child("reservations")

        fun addReservation(
            id: String,
            service: Service,
            date: String,
            time: String,
            title: String,
            location: String
        ) {
            Log.w("Debug", "Adding reservation")
            reservationsRef.child(id).setValue(Reservation(id, service, date, time, title, location))
        }

        addReservation(
        // UUID.randomUUID().toString(), // Generate a unique ID
            "1",
            Service("Tutoring", "Unlock your potential with personalized guidance and achieve academic success.", "res/", "~15€/h"),
            "17/12/22",
            "18:00",
            "Study Session",
            "Prague"
        )

        addReservation(
            // UUID.randomUUID().toString(), // Generate a unique ID
            "2",
            Service("Cleaning", "Sparkling clean solutions for your space. Professional cleaning services that make your home or office shine.", "", "~15€/h"),
            "16/12/22",
            "9:00",
            "Cleaning",
            "Prague"
        )

        addReservation(
            // UUID.randomUUID().toString(), // Generate a unique ID
            "3",
            Service("Training", "Boost your fitness journey with customized training. Personalized workouts, expert guidance.", "", "~15€/h"),
            "15/12/22",
            "11:00",
            "Cleaning",
            "Brno"
        )

        addReservation(
            // UUID.randomUUID().toString(), // Generate a unique ID
            "4",
            Service("Counselling", "Navigate life's challenges with tailored counseling. Compassionate support, insightful assistance.", "", "~15€/h"),
            "17/12/22",
            "16:00",
            "Counselling",
            "Prague"
        )

        val usersRef = database.child("users")

        val profileFragment = ProfileFragment()
        profileFragment.setUsersReference(usersRef)

        fun addUser(
            id: String,
            username: String,
            name: String,
            surname: String,
            age: Int
        ) {
            Log.w("Debug", "Adding User")
            usersRef.child(id).setValue(User(id, username, name, surname, age))
        }

        addUser(
            // UUID.randomUUID().toString(), // Generate a unique ID
            "1",
           "UsernameTest",
            "Name",
            "Surname",
            24
        )


    }
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp() || super.onSupportNavigateUp()
    }
}