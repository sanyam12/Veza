package com.unravel.veza

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class login:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signupbt: Button = findViewById(R.id.signup)
        val signinbt: Button = findViewById(R.id.signin)

        signinbt.setOnClickListener{
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

        signupbt.setOnClickListener{
            val intent: Intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
    }
}