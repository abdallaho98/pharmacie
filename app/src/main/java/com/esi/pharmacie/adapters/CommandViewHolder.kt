package com.esi.pharmacie.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.command_item.view.*

class CommandViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name = view.name
    val status = view.status
    val image = view.image
}
