package com.esi.pharmacie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.esi.pharmacie.R
import com.esi.pharmacie.fragments.SignUpFragment
import com.esi.pharmacie.models.Responce
import com.esi.pharmacie.models.StaticObjects
import com.esi.pharmacie.models.User
import com.esi.pharmacie.services.PharmacieService
import com.esi.pharmacie.services.RetrofitService
import com.esi.pharmacie.services.UserService
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = RetrofitService.retrofit.create(UserService::class.java)
        setContentView(R.layout.activity_auth)
        signup_button.setOnClickListener {
            //go to sign up fragment
            supportFragmentManager.beginTransaction().add(R.id.fragment, SignUpFragment(),"SignUp").commit()
        }

        login_button.setOnClickListener {
            service.login(phone.text.toString().trim(),password.text.toString().trim()).enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.e("user is ",response.body().toString())
                    StaticObjects.saveData(response.body(),"user",applicationContext)
                    val user = StaticObjects.getSavedData("user" , applicationContext) as User
                    StaticObjects.setUser(user)
                    change()
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(applicationContext , "Login Error" , Toast.LENGTH_LONG).show()
                }
            })
        }
    }


    fun change (){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
