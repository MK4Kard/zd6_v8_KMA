package com.companyname.zd7_v8_kma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.ClientEntity

class BuyerAdapter(
    private var list: List<ClientEntity>
) : RecyclerView.Adapter<BuyerAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val buyerId: TextView = view.findViewById(R.id.tvBuyerId)
        val orderId: TextView = view.findViewById(R.id.tvOrderId)
        val count: TextView = view.findViewById(R.id.tvCount)
        val price: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.buyer_s_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.buyerId.text = "Покупатель ID: ${item.login}"
        holder.orderId.text = "Мебель ID: ${item.orderId}"
        holder.count.text = "Количество: ${item.count}"
        holder.price.text = "Цена: ${item.price}"
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<ClientEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}
