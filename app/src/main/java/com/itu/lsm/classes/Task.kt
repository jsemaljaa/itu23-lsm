package com.itu.lsm.classes

import java.io.Serializable

data class Task(
    val id: String = "",
    val date: String = "",
    val time: String = "",
    val title: String = "",
    val loc: String = "",
) : Serializable
