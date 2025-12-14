package com.companyname.zd7_v8_kma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.ClientEntity

class ClientAdapter(
    private var list: List<ClientEntity>
) : RecyclerView.Adapter<ClientAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val orderId: TextView = view.findViewById(R.id.title)
        val companyId: TextView = view.findViewById(R.id.model)
        val count: TextView = view.findViewById(R.id.count)
        val price: TextView = view.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.orderId.text = "Мебель ID: ${item.orderId}"
        holder.companyId.text = "Продавец ID: ${item.company}"
        holder.count.text = "Количество: ${item.count}"
        holder.price.text = "Цена: ${item.price}"
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<ClientEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}
