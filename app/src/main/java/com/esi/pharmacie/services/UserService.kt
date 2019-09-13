package com.esi.pharmacie.services

import com.esi.pharmacie.models.Responce
import com.esi.pharmacie.models.Status
import com.esi.pharmacie.models.User
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @FormUrlEncoded
    @POST("/users/login")
    fun login(@Field("phone") phone : String , @Field("password") password : String): Call<User>


    @FormUrlEncoded
    @POST("/users/register")
    fun register(@Field("nss") nss : String , @Field("fname") fname : String,
                 @Field("lname") lname : String , @Field("adresse") adresse : String,
                 @Field("phone") phone : String ): Call<User>


    @FormUrlEncoded
    @POST("/users/password")
    fun changePassword(@Field("nss") nss : String , @Field("password") password : String): Call<Status>
}