package com.esi.pharmacie.models

import com.google.gson.annotations.SerializedName

class Pharmacie (

    @SerializedName("_id")
    val id : String ,
    val ville : String ,
    val wilaya : String ,
    val name : String ,
    val adresse : String? = "" ,
    val caisse : String ,
    val heurOverture : String? = "08:00" ,
    val heurFermeture : String? = "19:00",
    val phone : String? = "",
    val fb : String? = "" ,
    @SerializedName("location")
    val localisation : Localisation
    )