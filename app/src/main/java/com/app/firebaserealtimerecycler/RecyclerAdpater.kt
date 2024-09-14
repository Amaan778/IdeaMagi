package com.app.firebaserealtimerecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdpater(private val itemList: List<User>) : RecyclerView.Adapter<RecyclerAdpater.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val addressTextView:TextView =itemView.findViewById(R.id.addressTextView)
        val pincodeTextView:TextView=itemView.findViewById(R.id.pincodeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.nameTextView.text = currentItem.name
        holder.descriptionTextView.text = currentItem.lastname
        holder.addressTextView.text=currentItem.address
        holder.pincodeTextView.text=currentItem.pincode
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
