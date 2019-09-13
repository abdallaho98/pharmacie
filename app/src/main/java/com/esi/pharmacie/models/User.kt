package com.esi.pharmacie.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User (
    @SerializedName("_id")
    val id : String ,
    val nss : String ,
    val fname : String? = "",
    val lname : String? = "",
    val adresse : String? = "",
    val phone : String ,
    val password : String? = ""
) : Serializable