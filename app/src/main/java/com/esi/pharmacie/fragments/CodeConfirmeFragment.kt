package com.esi.pharmacie.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.esi.pharmacie.R
import com.esi.pharmacie.activities.AuthActivity
import com.esi.pharmacie.models.StaticObjects
import com.esi.pharmacie.models.User
import kotlinx.android.synthetic.main.fragment_code_confirme.*
import kotlinx.android.synthetic.main.fragment_code_confirme.view.*


class CodeConfirmeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_code_confirme, container, false)
        val user = arguments?.get("user") as User
        view.confirme.setOnClickListener {
            if(view.number.text.toString().trim().equals(user.password)){
                StaticObjects.setUser(user)
                StaticObjects.saveData(StaticObjects.getUser(), "user", activity?.applicationContext)
                activity!!.supportFragmentManager.beginTransaction().add(
                    R.id.fragment,
                    PasswordFragment(),"Password").commit()
            } else {
                Toast.makeText(activity?.applicationContext , "code érroné" , Toast.LENGTH_LONG).show()
            }
        }
        return view
    }


}
