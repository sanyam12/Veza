package com.unravel.veza

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class login:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signup: Button = findViewById(R.id.signup)
        val signin: Button = findViewById(R.id.signin)

        signin.setOnClickListener{
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

        signup.setOnClickListener{
            val intent: Intent = Intent(this, Signup::class.java)
            startActivity(intent)
            finish()
        }
    }
}