package com.esi.pharmacie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.graphics.drawable.Drawable




class MapFragment : Fragment() , OnMapReadyCallback {

    override fun onMapReady(googleMap: GoogleMap?) {

        val icon = BitmapDescriptorFactory.fromBitmap((resize(resources.getDrawable(R.drawable.ic_pharmacie)) as BitmapDrawable).bitmap)

        val markerOptions = MarkerOptions().position(LatLng(36.7201751,3.1889806))
            .title("Pharmacie Hamdaoui")
            .icon(icon)

        val mMarker = googleMap?.addMarker(markerOptions)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mMarker?.position, 15F))

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
