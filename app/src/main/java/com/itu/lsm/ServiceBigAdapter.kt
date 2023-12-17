// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.classes.Service

class ServiceBigAdapter(private var services: List<Service>, private val onServiceClick: (Service) -> Unit) : RecyclerView.Adapter<ServiceBigAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceImage: ImageView = view.findViewById(R.id.tvImage)
        val serviceTitle: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service_card_big, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.serviceTitle.text = service.title
        holder.serviceImage.setImageResource(getImageResourceBasedOnTitle(service.title))

        holder.itemView.setOnClickListener {
            onServiceClick(service)
        }
    }

    private fun getImageResourceBasedOnTitle(title: String): Int {
        return when (title) {
            "Tutoring" -> R.drawable.ai_tutoring
            "Cleaning" -> R.drawable.ai_cleaning
            "Counselling" -> R.drawable.ai_counselling
            "Training" -> R.drawable.ai_training
            "Cooking Lessons" -> R.drawable.ai_cooking
            "English Lessons" -> R.drawable.ai_english
            else -> R.drawable.logo
        }
    }

    override fun getItemCount() = services.size

    fun updateServices(newServices: List<Service>) {
        services = newServices
        notifyDataSetChanged()
    }
}
