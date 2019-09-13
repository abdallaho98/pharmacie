package com.esi.pharmacie.activities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.esi.pharmacie.R
import com.esi.pharmacie.models.Pharmacie
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_pharmacie_profile.*

class PharmacieProfileActivity : AppCompatActivity() , OnMapReadyCallback {

    lateinit var pharmacie : Pharmacie

    override fun onMapReady(p0: GoogleMap?) {
        val icon = BitmapDescriptorFactory.fromBitmap((resize(resources.getDrawable(R.drawable.ic_pharmacie)) as BitmapDrawable).bitmap)

        val markerOptions = MarkerOptions().position(LatLng(pharmacie.localisation.coordinates[0],pharmacie.localisation.coordinates[1]))
            .title(pharmacie.name)
            .icon(icon)
        val mMarker = p0?.addMarker(markerOptions)
        p0?.moveCamera(CameraUpdateFactory.newLatLngZoom(mMarker?.position, 11F))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacie_profile)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeButtonEnabled(true)
        pharmacie = intent.extras["pharmacie"] as Pharmacie
        val pharmacie = intent.extras["pharmacie"] as Pharmacie
        name.text = pharmacie.name
        actionbar!!.title = pharmacie.name
        ville.text = pharmacie.ville
        wilaya.text = pharmacie.wilaya
        adress.text = pharmacie.adresse
        phone.text = pharmacie.phone
        facebook.text = pharmacie.fb
        caisse.text = pharmacie.caisse
        heurover.text = pharmacie.heurOverture
        fermheur.text = pharmacie.heurFermeture
        val mapF = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapF.getMapAsync (this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun resize(image: Drawable): Drawable {
        val b = (image as BitmapDrawable).bitmap
        val bitmapResized = Bitmap.createScaledBitmap(b, 100, 100, false)
        return BitmapDrawable(resources, bitmapResized)
    }

}
