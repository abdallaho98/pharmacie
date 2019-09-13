package com.esi.pharmacie.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esi.pharmacie.R
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        view.confirm.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().add(
                R.id.fragment,
                CodeConfirmeFragment(),"Code").commit()
        }
        return view
    }


}
