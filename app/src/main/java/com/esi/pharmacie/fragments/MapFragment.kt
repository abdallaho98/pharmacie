package com.esi.pharmacie.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
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
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.esi.pharmacie.R
import com.esi.pharmacie.models.Responce
import com.esi.pharmacie.services.PharmacieService
import com.esi.pharmacie.services.RetrofitService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CircleOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragment : Fragment() , OnMapReadyCallback {

    private var locationManager : LocationManager? = null
    private lateinit var googleMapI : GoogleMap
    private lateinit var positionGlobal : LatLng
    private val service = RetrofitService.retrofit.create(PharmacieService::class.java)


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {

        googleMapI = googleMap!!
        val icon = BitmapDescriptorFactory.fromBitmap((resize(resources.getDrawable(R.drawable.ic_pharmacie)) as BitmapDrawable).bitmap)
        //localisation
        var locationManager= activity?.applicationContext?.getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3, 3f, locationListener)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        //init map
        val mapF = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapF.getMapAsync(this)
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    //resize icon pharmacie
    private fun resize(image: Drawable): Drawable {
        val b = (image as BitmapDrawable).bitmap
        val bitmapResized = Bitmap.createScaledBitmap(b, 100, 100, false)
        return BitmapDrawable(resources, bitmapResized)
    }

    //localisation change
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            googleMapI.clear()
            drawCircle(LatLng(location.latitude,location.longitude))
            googleMapI.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude), 13F))
            updateMap(LatLng(location.latitude,location.longitude))
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    //draw circle on my localization
     private fun drawCircle(point : LatLng){
                val circleOptions = CircleOptions()
                circleOptions.center(point)
                circleOptions.radius(50.0)
                circleOptions.strokeColor(Color.BLUE)
                circleOptions.fillColor(Color.BLUE)
                circleOptions.strokeWidth(1F)
                googleMapI.addCircle(circleOptions)
            }


    //get near pharmacies
    private fun updateMap(pos : LatLng){
        val icon = BitmapDescriptorFactory.fromBitmap((resize(resources.getDrawable(R.drawable.ic_pharmacie)) as BitmapDrawable).bitmap)
        service.nearPharmacies(pos.latitude,pos.longitude).enqueue(object: Callback<Responce> {
            override fun onResponse(call: Call<Responce>, response: Response<Responce>) {
                val allPharmacie = response.body().pharmacies
                allPharmacie?.let {
                    for( pharmacie in it) {
                        Log.d("Pharmacie","Pharmacie Nom : ${pharmacie.name}")
                        val markerOptions = MarkerOptions().position(LatLng(pharmacie.localisation.coordinates[0],pharmacie.localisation.coordinates[1]))
                            .title(pharmacie.name)
                            .icon(icon)
                        val mMarker = googleMapI.addMarker(markerOptions)
                    }
                }
            }
            override fun onFailure(call: Call<Responce>, t: Throwable) {
                Log.e("TAN", "Error : $t")
            }
        })
    }
}
