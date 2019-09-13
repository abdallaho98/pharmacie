package com.esi.pharmacie.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.esi.pharmacie.R
import com.esi.pharmacie.models.User
import com.esi.pharmacie.services.RetrofitService
import com.esi.pharmacie.services.UserService
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response





class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val service = RetrofitService.retrofit.create(UserService::class.java)
        view.confirm.setOnClickListener {
            service.register(num_ss.text.toString().trim() , prenom.text.toString().trim() , nom.text.toString().trim() ,
                adress.text.toString().trim() , number.text.toString().trim()).enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.e("user is ",response.body().toString())
                    val bundle = Bundle()
                    bundle.putSerializable("user", response.body())
                    val codeConfirmeFragment = CodeConfirmeFragment()
                    codeConfirmeFragment.arguments = bundle
                    activity!!.supportFragmentManager.beginTransaction().add(
                        R.id.fragment,
                        codeConfirmeFragment,"Code").commit()
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity?.applicationContext , "Register Error" , Toast.LENGTH_LONG).show()
                }
            })
        }
        return view
    }


}
