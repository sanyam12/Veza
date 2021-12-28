package com.unravel.veza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val a = 4000
        Handler().postDelayed({
            val intent: Intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }, a.toLong())
    }
}