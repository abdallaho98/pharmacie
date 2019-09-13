package com.esi.pharmacie


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.esi.pharmacie.adapters.PharmacieAdapter
import com.esi.pharmacie.models.Commune
import com.esi.pharmacie.models.Pharmacie
import com.esi.pharmacie.models.Responce
import com.esi.pharmacie.models.Wilaya
import com.esi.pharmacie.services.PharmacieService
import com.esi.pharmacie.services.RetrofitService
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_pharmacies.*
import kotlinx.android.synthetic.main.fragment_pharmacies.view.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException


class PharmaciesFragment : Fragment() {

    private val wilaya : ArrayList<Wilaya> = ArrayList()
    private val commune : ArrayList<Commune> = ArrayList()
    private  val wilayaName : ArrayList<String> = ArrayList()
    private var pharmacies : ArrayList<Pharmacie> = ArrayList()
    private val service = RetrofitService.retrofit.create(PharmacieService::class.java)
    private lateinit var adapter : PharmacieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonArray = JSONArray(readFileFromRawDirectory(R.raw.wilayas))
        for(i in 0 until jsonArray.length()){
            wilaya.add(Wilaya(jsonArray.getJSONObject(i).getString("id").toInt() , jsonArray.getJSONObject(i).getString("nom")))
            wilayaName.add(wilaya[i].id.toString().plus(" ").plus(wilaya[i].name))
        }
        val jsonArrayCommune = JSONArray(readFileFromRawDirectory(R.raw.communes))
        for(i in 0 until jsonArrayCommune.length()){
            commune.add(Commune(jsonArrayCommune.getJSONObject(i).getString("id").toInt(),jsonArrayCommune.getJSONObject(i).getString("wilaya_id").toInt() , jsonArrayCommune.getJSONObject(i).getString("nom")))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pharmacies, container, false)
        view.list.layoutManager = LinearLayoutManager(activity)
        adapter = PharmacieAdapter(pharmacies, context!!)
        view.list.adapter = adapter
        view.wilayas.adapter = ArrayAdapter<String>(activity, R.layout.item_spinner , wilayaName)
        fetchPharmacies("","")


        view.wilayas?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                val selectedId = selectedItem.subSequence(0,2).trim().toString().toInt()
                val communeSelected : ArrayList<Commune> = ArrayList()
                val communeName : ArrayList<String> = ArrayList()
                for (i in 0 until commune.size)
                    if(selectedId == commune[i].wilaya_id) {
                        communeSelected.add(commune[i])
                        communeName.add(commune[i].name)
                    }
                view.communes.adapter = ArrayAdapter<String>(activity, R.layout.item_spinner , communeName )
                Log.e("Selected",selectedItem.substring(2))
                fetchPharmacies("",selectedItem.substring(2).trim())
            }
        }

        view.communes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Log.e("Selected",selectedItem.substring(2))
                fetchPharmacies(selectedItem.trim(),view?.wilayas?.selectedItem.toString().substring(2).trim())
            }

        }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = PharmaciesFragment()
    }

    override fun onDestroyView() {
        view?.communes?.onItemSelectedListener  = null
        view?.wilayas?.onItemSelectedListener  = null
        super.onDestroyView()
    }


    fun fetchPharmacies(ville : String , wilaya : String){
        service.pharmacies(ville,wilaya).enqueue(object: Callback<Responce> {
            override fun onResponse(call: Call<Responce>, response: Response<Responce>) {
                val allPharmacie = response.body().pharmacies
                Log.e("TAN", "Error : ${allPharmacie?.size}")
                pharmacies.clear()
                if (allPharmacie != null) {
                    pharmacies.addAll(allPharmacie)
                }
                if(allPharmacie?.size!! > 0){
                    adapter.change(allPharmacie)
                } else { adapter.change(ArrayList()) }
                adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<Responce>, t: Throwable) {
                Log.e("TAN", "Error : $t")
            }
        })
    }



    private fun readFileFromRawDirectory(resourceId: Int): String {
        val iStream = context!!.resources.openRawResource(resourceId)
        var byteStream: ByteArrayOutputStream? = null
        try {
            val buffer = ByteArray(iStream.available())
            iStream.read(buffer)
            byteStream = ByteArrayOutputStream()
            byteStream!!.write(buffer)
            byteStream!!.close()
            iStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteStream!!.toString()
    }


}
