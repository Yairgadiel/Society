package com.gy.society.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem = items[position]
        holder.nameTv.text = currItem.name
        holder.countTv.text = currItem.count.toString()
    }

    override fun getItemCount() = items.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView
        val countTv: TextView

        init {
            nameTv = itemView.findViewById(R.id.item_text)
            countTv = itemView.findViewById(R.id.item_count)
        }

    }
}
