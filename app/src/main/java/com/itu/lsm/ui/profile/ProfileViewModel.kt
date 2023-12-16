package com.itu.lsm.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }

    private val _name = MutableLiveData<String>().apply {
        value = "Name"
    }

    private val _surname = MutableLiveData<String>().apply {
        value = "Surname"
    }

    val text: LiveData<String> = _text
    val name: LiveData<String> = _name
    val surname: LiveData<String> = _surname

}