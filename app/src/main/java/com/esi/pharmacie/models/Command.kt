package com.esi.pharmacie.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Command (
    @SerializedName("_id")
    val id : String ,
    @SerializedName("status")
    val status : String ,
    val image : String
) : Serializable