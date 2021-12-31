package com.unravel.veza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val a = 4000

        if(FirebaseAuth.getInstance().currentUser==null){
            Handler().postDelayed({
                val intent: Intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()
            }, a.toLong())
        }else
        {
            Handler().postDelayed({
                val intent: Intent = Intent(this, home::class.java)
                startActivity(intent)
                finish()
            }, a.toLong())
        }
        
    }
}