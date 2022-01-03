package com.unravel.veza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

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
            val db = FirebaseFirestore.getInstance()
            val mauth = FirebaseAuth.getInstance()
            db.collection("desc").document(mauth.uid.toString()).get().addOnSuccessListener {
                if(!it.contains("displayName"))
                {
                    Handler().postDelayed({
                        val intent = Intent(this, signup_details::class.java)
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
        
    }
}