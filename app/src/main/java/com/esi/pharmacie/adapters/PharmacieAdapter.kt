package com.esi.pharmacie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esi.pharmacie.R
import com.esi.pharmacie.models.Pharmacie

class PharmacieAdapter (var items : ArrayList<Pharmacie>, val context: Context) :
    RecyclerView.Adapter<PharmacieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacieViewHolder {
        return PharmacieViewHolder(LayoutInflater.from(context).inflate(R.layout.pharmacie_item, parent, false))
    }

    override fun getItemCount(): Int { return items.size }

    fun change (newItems : ArrayList<Pharmacie>)  {items.clear() ; items.addAll(newItems)}

    override fun onBindViewHolder(holder: PharmacieViewHolder, position: Int) {
        holder?.name?.text = items.get(position).name
        holder?.adress?.text = items.get(position).wilaya + " " + items.get(position).ville + " " + items.get(position).adresse
    }
}