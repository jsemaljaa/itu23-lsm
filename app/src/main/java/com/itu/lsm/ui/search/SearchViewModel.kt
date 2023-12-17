package com.itu.lsm.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.itu.lsm.classes.Service

class SearchViewModel : ViewModel() {

    private val _services = MutableLiveData<List<Service>?>()
    val services: MutableLiveData<List<Service>?> = _services
    private val fullServicesList = mutableListOf<Service>()

    private val database = Firebase.database
    private val servicesRef = database.getReference("services")


    init {
        fetchServices()
    }

    fun fetchServices() {
        servicesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                fullServicesList.clear() // Clear the existing list
                dataSnapshot.children.mapNotNullTo(fullServicesList) {
                    it.getValue<Service>(Service::class.java)
                }
                _services.value = fullServicesList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadServices:onCancelled", databaseError.toException())
            }
        })
    }

    // Search function to filter services based on query
    fun searchServices(query: String) {
        if (query.isEmpty()) {
            _services.value = fullServicesList
        } else {
            val filteredList = fullServicesList.filter {
                it.title.contains(query, ignoreCase = true)
            }
            _services.value = filteredList
        }
    }

}
