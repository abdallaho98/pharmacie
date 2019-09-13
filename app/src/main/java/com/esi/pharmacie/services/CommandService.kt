package com.esi.pharmacie.services

import com.esi.pharmacie.models.Command
import com.esi.pharmacie.models.CommandResponce
import com.esi.pharmacie.models.Pharmacie
import com.esi.pharmacie.models.User
import retrofit2.Call
import retrofit2.http.*

interface CommandService {

    @GET("/commands/")
    fun commands(@Header("user") user : String): Call<CommandResponce>

    @FormUrlEncoded
    @POST("/commands/add")
    fun addCommand(@Field("user") user : String , @Field("pharmacies") pharmacie : String ,
                   @Field("image") image : String , @Field("status") status : String ): Call<Command>
}