package com.dave.iaa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.collections.ArrayList

class PlaceAdapter(var list: ArrayList<Place>, var placePicked: (Place) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return StringViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val placeView = holder as StringViewHolder
        val place = list[position]
        placeView.itemView.text.text = place.name
        placeView.itemView.setOnClickListener { onItemClicked(place) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private inner class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text: TextView

        init {
            text = itemView.findViewById(R.id.text)
        }
    }

    private fun onItemClicked(place: Place) {
        placePicked(place)
    }
}