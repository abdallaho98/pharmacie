package com.esi.pharmacie.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.esi.pharmacie.R
import com.esi.pharmacie.models.Command
import com.esi.pharmacie.models.Pharmacie
import com.esi.pharmacie.models.StaticObjects
import com.esi.pharmacie.models.User
import com.esi.pharmacie.services.CommandService
import com.esi.pharmacie.services.RetrofitService
import com.esi.pharmacie.services.UserService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_pharmacie_profile.*
import kotlinx.android.synthetic.main.activity_pharmacie_profile.phone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class PharmacieProfileActivity : AppCompatActivity() , OnMapReadyCallback {

    lateinit var pharmacie : Pharmacie
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null


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

        commander.setOnClickListener {
            firebaseStore = FirebaseStorage.getInstance()
            storageReference = FirebaseStorage.getInstance().reference
            launchGallery()
        }
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


    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                uploadImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage(){
        if(filePath != null){
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    saveCommand(downloadUri.toString())
                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveCommand(link : String){
        val service = RetrofitService.retrofit.create(CommandService::class.java)
        service.addCommand(StaticObjects.getUser().id,pharmacie.id,link,"Progres").enqueue(object: Callback<Command> {
            override fun onResponse(call: Call<Command>, response: Response<Command>) {
                Toast.makeText(applicationContext , "Upload Done " , Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<Command>, t: Throwable) {
                Toast.makeText(applicationContext , "Upload Error" , Toast.LENGTH_LONG).show()
            }
        })
    }


}
