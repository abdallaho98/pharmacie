package com.esi.pharmacie


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.esi.pharmacie.models.Wilaya
import kotlinx.android.synthetic.main.fragment_pharmacies.view.*
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.IOException


class PharmaciesFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pharmacies, container, false)
        val jsonArray = JSONArray(readFileFromRawDirectory(R.raw.wilayas))
        val wilaya : ArrayList<Wilaya> = ArrayList()
        val wilayaName : ArrayList<String> = ArrayList()
        for(i in 0 until jsonArray.length()){
            wilaya.add(Wilaya(jsonArray.getJSONObject(i).getString("id").toInt() , jsonArray.getJSONObject(i).getString("nom")))
            wilayaName.add(wilaya[i].name)
        }

        view.wilayas.adapter = ArrayAdapter<String>(activity, R.layout.item_spinner , wilayaName)
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = PharmaciesFragment()
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
