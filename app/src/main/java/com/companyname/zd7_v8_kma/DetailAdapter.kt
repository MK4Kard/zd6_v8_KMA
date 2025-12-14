package com.companyname.zd7_v8_kma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.SupplierEntity
import kotlin.collections.get

class DetailAdapter(private var list: List<SupplierEntity>,
                    private val onClick: (SupplierEntity) -> Unit) : RecyclerView.Adapter<DetailAdapter.SupplierViewHolder>() {
    class SupplierViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val price: TextView = view.findViewById(R.id.price)
        val char: TextView = view.findViewById(R.id.char_)
        val count: TextView = view.findViewById(R.id.count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_item2, parent, false)
        return SupplierViewHolder(view)
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val item = list[position]

        holder.title.text = item.detail
        holder.price.text = "Цена: ${item.price}₽"
        holder.char.text = "Характеристика: ${item.characteristics}"
        holder.count.text = "Количество: ${item.count}"

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<SupplierEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}