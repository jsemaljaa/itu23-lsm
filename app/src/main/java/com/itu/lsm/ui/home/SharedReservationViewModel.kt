// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itu.lsm.classes.Service

class SharedReservationViewModel : ViewModel() {
    private val _selectedService = MutableLiveData<Service>()
    val selectedService: LiveData<Service> = _selectedService

    private val _selectedLocation = MutableLiveData<String>()
    val selectedLocation: LiveData<String> = _selectedLocation

    private val _selectedDateTime = MutableLiveData<String>()
    val selectedDateTime: LiveData<String> = _selectedDateTime

    fun selectService(service: Service) {
        _selectedService.value = service
    }

    fun selectLocation(location: String) {
        _selectedLocation.value = location
    }

    fun selectDateTime(datetime: String) {
        _selectedDateTime.value = datetime
    }
}

