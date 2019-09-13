package com.esi.pharmacie.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esi.pharmacie.R
import com.esi.pharmacie.activities.PharmacieProfileActivity
import com.esi.pharmacie.models.Command
import com.esi.pharmacie.models.Pharmacie
import com.squareup.picasso.Picasso

class CommandAdapter (var items : ArrayList<Command>, val context: Activity) :
    RecyclerView.Adapter<CommandViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandViewHolder {
        return CommandViewHolder(LayoutInflater.from(context).inflate(R.layout.command_item, parent, false))
    }

    override fun getItemCount(): Int { return items.size }

    fun change (newItems : ArrayList<Command>)  {items.clear() ; items.addAll(newItems)}

    override fun onBindViewHolder(holder: CommandViewHolder, position: Int) {
        holder?.name?.text = items[position].id
        holder?.status?.text = items[position].status
        val picasso = Picasso.get()
        picasso.load(items[position].image)
            .into(holder.image)

    }
}