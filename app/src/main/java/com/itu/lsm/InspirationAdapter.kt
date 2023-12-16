package com.itu.lsm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itu.lsm.classes.InspirationItem

class InspirationAdapter(private val dataset: List<InspirationItem>) :
    RecyclerView.Adapter<InspirationAdapter.InspirationViewHolder>() {

    class InspirationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rateTextView: TextView = view.findViewById(R.id.tvPrice)
        val titleTextView: TextView = view.findViewById(R.id.tvTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspirationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inspiration_card, parent, false)
        return InspirationViewHolder(view)
    }

    override fun onBindViewHolder(holder: InspirationViewHolder, position: Int) {
        val item = dataset[position]
        holder.rateTextView.text = item.price
        holder.titleTextView.text = item.title
        holder.descriptionTextView.text = item.description
    }

    override fun getItemCount() = dataset.size
}
