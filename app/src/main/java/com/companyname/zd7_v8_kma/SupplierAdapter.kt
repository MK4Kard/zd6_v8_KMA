package com.companyname.zd7_v8_kma

import com.companyname.zd7_v8_kma.Login.SupplierEntity
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater

class SupplierAdapter(private var list: List<SupplierEntity>,
                      private val onItemClick: (SupplierEntity) -> Unit) : RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>() {

    class SupplierViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val price: TextView = view.findViewById(R.id.price)
        val count: TextView = view.findViewById(R.id.count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_item, parent, false)
        return SupplierViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.detail
        holder.price.text = "Цена: ${item.price}₽"
        holder.count.text = "Количество: ${item.count}"

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<SupplierEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}