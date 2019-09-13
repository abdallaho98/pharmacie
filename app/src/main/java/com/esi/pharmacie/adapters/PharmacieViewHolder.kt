package com.esi.pharmacie.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pharmacie_item.view.*

class PharmacieViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val name = view.name
    val adress = view.adress
}
