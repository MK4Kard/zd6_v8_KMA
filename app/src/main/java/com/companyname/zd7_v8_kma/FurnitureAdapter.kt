package com.companyname.zd7_v8_kma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.WorkerEntity

class FurnitureAdapter(private var list: List<WorkerEntity>,
                       private val onClick: (WorkerEntity) -> Unit) : RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>() {
    class FurnitureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val price: TextView = view.findViewById(R.id.price)
        val model: TextView = view.findViewById(R.id.model)
        val count: TextView = view.findViewById(R.id.count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.furniture_item, parent, false)
        return FurnitureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val item = list[position]

        holder.title.text = item.furniture
        holder.price.text = "Цена: ${item.price}₽"
        holder.model.text = item.model
        holder.count.text = "Количество: ${item.count}"

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<WorkerEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}