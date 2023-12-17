package com.itu.lsm.classes

class Reservation(
    val id: String,
    private val service: Service,
    val date: String,
//    private val date: Date,   // todo For making future reservations - date when reservation was created
    val time: String,
    val title: String,
    val location: String
) {

    fun getFormattedDetails(): String {
        return "Reservation ID: $id\nDate: $date\n Service: $service\n Time: $time\nTitle: $title\nLocation: $location"
    }
}