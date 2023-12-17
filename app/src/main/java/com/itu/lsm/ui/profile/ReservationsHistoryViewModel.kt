// Author: Anastasiia Berezovska xberez04

package com.itu.lsm.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itu.lsm.classes.Reservation

class ReservationsHistoryViewModel : ViewModel() {
    private val _reservation = MutableLiveData<Reservation>()
    val reservation: LiveData<Reservation> get() = _reservation

    // todo add data to _reservation from db

    private val _details = MutableLiveData<String>().apply {
        value = "Details"
    }

    private val _title = MutableLiveData<String>().apply {
        value = "Title"
    }

    private val _service = MutableLiveData<String>().apply {
        value = "Service"
    }

    private val _date = MutableLiveData<String>().apply {
        value = "Date"
    }

    private val _location = MutableLiveData<String>().apply {
        value = "Location"
    }

    val title: LiveData<String> = _title
    val details: LiveData<String> = _details
    val service: LiveData<String> = _service
    val date: LiveData<String> = _location
}
