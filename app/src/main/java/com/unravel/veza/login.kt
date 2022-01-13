package com.unravel.veza

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class login:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val signupbt: Button = findViewById(R.id.signup)
        val signinbt: Button = findViewById(R.id.signin)
        val phonebt: Button = findViewById(R.id.button11)

        if(intent.getStringExtra("bool")=="true")
        {
            Snackbar.make(findViewById(R.id.imageView), "You were logged out", 1000)
                .setAction("Action", null).show()
        }

        signinbt.setOnClickListener{
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish()
        }

        signupbt.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

//        phonebt.setOnClickListener{
//            val intent = Intent(this, PhoneLogin::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}