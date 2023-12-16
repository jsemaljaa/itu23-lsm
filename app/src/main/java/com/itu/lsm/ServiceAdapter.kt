package com.itu.lsm

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.classes.Service

class ServiceAdapter(private var dataset: List<Service>) : RecyclerView.Adapter<ServiceAdapter.InspirationViewHolder>() {

    class InspirationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val priceTextView: TextView = view.findViewById(R.id.tvPrice)
        val titleTextView: TextView = view.findViewById(R.id.tvTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.tvDescription)
        val imageView: ImageView = view.findViewById(R.id.tvImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspirationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service_card, parent, false)
        return InspirationViewHolder(view)
    }

    override fun onBindViewHolder(holder: InspirationViewHolder, position: Int) {
        val item = dataset[position]
        holder.priceTextView.text = item.price
        holder.titleTextView.text = item.title
        holder.descriptionTextView.text = item.description

        holder.imageView.setImageResource(getImageResourceBasedOnTitle(item.title))
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

    override fun getItemCount() = dataset.size

    fun updateServices(newServices: List<Service>) {
        dataset = newServices
        notifyDataSetChanged()
    }
}
