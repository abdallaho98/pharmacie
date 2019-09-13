package com.esi.pharmacie.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.esi.pharmacie.R
import com.esi.pharmacie.adapters.CommandAdapter
import com.esi.pharmacie.adapters.PharmacieAdapter
import com.esi.pharmacie.models.*
import com.esi.pharmacie.services.CommandService
import com.esi.pharmacie.services.PharmacieService
import com.esi.pharmacie.services.RetrofitService
import kotlinx.android.synthetic.main.fragment_command.view.*
import kotlinx.android.synthetic.main.fragment_pharmacies.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommandFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_command, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(StaticObjects.getSavedData("user",activity?.applicationContext) != null){
            StaticObjects.setUser(StaticObjects.getSavedData("user",activity?.applicationContext) as User)
        }
        val user = StaticObjects.getUser()
        if(user == null){
            view.commands.visibility = View.INVISIBLE
            view.auth.visibility = View.VISIBLE
        } else {
            val service = RetrofitService.retrofit.create(CommandService::class.java)
            view.commands.layoutManager = LinearLayoutManager(activity)
            val allCommands = ArrayList<Command>()
            val adapter = CommandAdapter(allCommands, activity!!)
            view.commands.adapter = adapter
            Log.e("id user " , user.id)
            service.commands(user.id).enqueue(object: Callback<CommandResponce> {
                override fun onResponse(call: Call<CommandResponce>, response: Response<CommandResponce>) {
                    val newCommands = response.body().commands
                    Log.e("Succes", "Error : ${response.body().commands[0]}")
                    adapter.change(newCommands)
                    adapter.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<CommandResponce>, t: Throwable) {
                    Log.e("ERROR", "Error : ${t.message}")
                }
            })
        }
    }


    companion object {
         @JvmStatic
        fun newInstance() = CommandFragment()
    }
}
