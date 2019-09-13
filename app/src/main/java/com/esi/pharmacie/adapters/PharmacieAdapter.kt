package com.esi.pharmacie.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esi.pharmacie.R
import com.esi.pharmacie.activities.PharmacieProfileActivity
import com.esi.pharmacie.models.Pharmacie

class PharmacieAdapter (var items : ArrayList<Pharmacie>, val context: Activity) :
    RecyclerView.Adapter<PharmacieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacieViewHolder {
        return PharmacieViewHolder(LayoutInflater.from(context).inflate(R.layout.pharmacie_item, parent, false))
    }

    override fun getItemCount(): Int { return items.size }

    fun change (newItems : ArrayList<Pharmacie>)  {items.clear() ; items.addAll(newItems)}

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PharmacieViewHolder, position: Int) {
        holder?.name?.text = items[position].name
        holder?.adress?.text = items[position].wilaya + " " + items[position].ville + " " + items[position].adresse
        holder.itemView.setOnClickListener {
            val intent = Intent( context , PharmacieProfileActivity::class.java)
            intent.putExtra("pharmacie" , items[position] )
            context.startActivity(intent)
        }
    }
}