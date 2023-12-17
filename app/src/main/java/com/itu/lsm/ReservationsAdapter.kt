// Author: Anastasiia Berezovska

package com.itu.lsm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.classes.Reservation

class ReservationsAdapter(
    private val reservations: List<Reservation>,
    private val removeItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewReservationTitle)
        val detailsTextView: TextView = itemView.findViewById(R.id.textViewReservationDetails)
        val removeButton: Button = itemView.findViewById(R.id.removeButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation_card, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]

        holder.titleTextView.text = reservation.title
        holder.detailsTextView.text = "Details: ${reservation.date}, ${reservation.time}, ${reservation.location}"

        holder.removeButton.setOnClickListener {
            removeItemClickListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return reservations.size
    }
}

