package com.unravel.veza

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class PhoneLogin: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_login)
        val phoneNo: EditText = findViewById(R.id.phone_text2)
        val otp: Button = findViewById(R.id.button9)

        otp.setOnClickListener{
            val s:String = phoneNo.text.toString()
            val i: Int = s.toInt()
            val temp: TextView = findViewById(R.id.pass)
            temp.text = i.toString()
        }
    }
}