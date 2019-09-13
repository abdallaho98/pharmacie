package com.esi.pharmacie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esi.pharmacie.R
import com.esi.pharmacie.fragments.SignUpFragment
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        signup_button.setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.fragment, SignUpFragment(),"SignUp").commit()
        }

        login_button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
