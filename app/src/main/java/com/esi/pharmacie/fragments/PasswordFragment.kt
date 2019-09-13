package com.esi.pharmacie.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.esi.pharmacie.R
import com.esi.pharmacie.activities.AuthActivity
import com.esi.pharmacie.models.StaticObjects
import com.esi.pharmacie.models.Status
import com.esi.pharmacie.models.User
import com.esi.pharmacie.services.RetrofitService
import com.esi.pharmacie.services.UserService
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_password.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_password, container, false)
        val service = RetrofitService.retrofit.create(UserService::class.java)
        val user =  StaticObjects.getUser() as User
        Log.e("nss ",user.nss)
        view.confirme.setOnClickListener {
            service.changePassword(user.nss , number.text.toString().trim())
                .enqueue(object: Callback<Status> {
                override fun onResponse(call: Call<Status>, response: Response<Status>) {
                    (activity as AuthActivity).change()
                }
                override fun onFailure(call: Call<Status>, t: Throwable) {
                    Toast.makeText(activity?.applicationContext , "Password Error" , Toast.LENGTH_LONG).show()
                }
            })
        }
        return view
    }



}
