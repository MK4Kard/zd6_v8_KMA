package com.companyname.zd7_v8_kma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.companyname.zd7_v8_kma.Login.LoginEntity

class BuyerDetailAdapter(
    private var list: List<LoginEntity>) : RecyclerView.Adapter<BuyerDetailAdapter.BuyerViewHolder>() {

    class BuyerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val email: TextView = view.findViewById(R.id.mail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.buyer_item, parent, false)
        return BuyerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuyerViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.login
        holder.email.text = item.mail
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<LoginEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}
