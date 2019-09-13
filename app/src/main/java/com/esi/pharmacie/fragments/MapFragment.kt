package com.esi.pharmacie.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.esi.pharmacie.R
import com.esi.pharmacie.models.Responce
import com.esi.pharmacie.services.PharmacieService
import com.esi.pharmacie.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragment : Fragment() , OnMapReadyCallback {

    override fun onMapReady(googleMap: GoogleMap?) {

        val icon = BitmapDescriptorFactory.fromBitmap((resize(resources.getDrawable(R.drawable.ic_pharmacie)) as BitmapDrawable).bitmap)

        val service = RetrofitService.retrofit.create(PharmacieService::class.java)

        service.nearPharmacies(35.1531,3.123).enqueue(object: Callback<Responce> {
            override fun onResponse(call: Call<Responce>, response: Response<Responce>) {
                val allPharmacie = response.body().pharmacies
                allPharmacie?.let {
                    for( pharmacie in it) {
                        Log.d("Pharmacie","Pharmacie Nom : ${pharmacie.name}")
                        val markerOptions = MarkerOptions().position(LatLng(pharmacie.localisation.coordinates[0],pharmacie.localisation.coordinates[1]))
                            .title(pharmacie.name)
                            .icon(icon)
                        val mMarker = googleMap?.addMarker(markerOptions)
                        //googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mMarker?.position, 15F))
                    }
                }
            }
            override fun onFailure(call: Call<Responce>, t: Throwable) {
                Log.e("TAN", "Error : $t")
            }
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapF = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapF.getMapAsync(this)
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    private fun resize(image: Drawable): Drawable {
        val b = (image as BitmapDrawable).bitmap
        val bitmapResized = Bitmap.createScaledBitmap(b, 100, 100, false)
        return BitmapDrawable(resources, bitmapResized)
    }
}
