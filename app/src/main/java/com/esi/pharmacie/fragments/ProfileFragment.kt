package com.esi.pharmacie.fragments


import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esi.pharmacie.R
import com.esi.pharmacie.activities.AuthActivity
import com.esi.pharmacie.models.StaticObjects
import com.esi.pharmacie.models.User
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(StaticObjects.getSavedData("user",activity?.applicationContext) != null){
            StaticObjects.setUser(StaticObjects.getSavedData("user",activity?.applicationContext) as User)
        }
        val user = StaticObjects.getUser()
        if(user == null){
            view.abbr.visibility = View.GONE
            view.fullname.visibility = View.GONE
            view.logout.visibility = View.GONE
            view.auth.visibility = View.VISIBLE
        } else {
            view.abbr.text = user.fname?.get(0).toString() + user.lname?.get(0).toString()
            view.fullname.text = user.fname + " "+ user.lname
        }

        view.auth.setOnClickListener {
            startActivity(Intent(activity , AuthActivity::class.java))
            activity?.finish()
        }

        view.logout.setOnClickListener {
            StaticObjects.saveData(null,"user",activity?.applicationContext)
            StaticObjects.setUser(null)
            startActivity(Intent(activity , AuthActivity::class.java))
            activity?.finish()
        }


    }


    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
