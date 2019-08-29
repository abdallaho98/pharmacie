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
import com.esi.pharmacie.models.Commune
import com.esi.pharmacie.models.Wilaya
import kotlinx.android.synthetic.main.fragment_pharmacies.view.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.IOException


class PharmaciesFragment : Fragment() {

    private val wilaya : ArrayList<Wilaya> = ArrayList()
    private val commune : ArrayList<Commune> = ArrayList()
    private  val wilayaName : ArrayList<String> = ArrayList()

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

        view.wilayas.adapter = ArrayAdapter<String>(activity, R.layout.item_spinner , wilayaName)



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
                Log.e("Selected","Now")
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
