package com.esi.pharmacie.services

import com.esi.pharmacie.models.Pharmacie
import com.esi.pharmacie.models.Responce
import com.esi.pharmacie.models.Wilaya
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PharmacieService {

    @GET("/pharmacies/near")
    @SerializedName("pharmacies")
    fun nearPharmacies(@Query("lat") lat: Double, @Query("lng") lng : Double ): Call<Responce>


    @GET("/pharmacies")
    fun pharmacies(@Query("ville") ville: String, @Query("wilaya") wilaya: String ): Call<Responce>
}