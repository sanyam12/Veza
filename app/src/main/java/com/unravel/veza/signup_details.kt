package com.unravel.veza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class signup_details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_details)

        val save: Button = findViewById(R.id.button6)
        val enterFirstName = findViewById<EditText>(R.id.editTextTextPersonName)
        val enterLastName = findViewById<EditText>(R.id.editTextTextPersonName2)
        val enterDisplayName = findViewById<EditText>(R.id.editTextTextPersonName3)
        save.setOnClickListener{
            when{
                TextUtils.isEmpty(enterFirstName.text.toString().trim{it<=' '})->{
                    Snackbar.make(save,"Enter Valid First Name", Snackbar.LENGTH_SHORT)
                        .setAction("action", null).show()
                }
                TextUtils.isEmpty(enterLastName.text.toString().trim{it<=' '})-> {
                    Snackbar.make(save,"Enter Valid Last Name", Snackbar.LENGTH_SHORT)
                        .setAction("action", null).show()
                }
                TextUtils.isEmpty(enterDisplayName.text.toString().trim{it<=' '})-> {
                    Snackbar.make(save,"Enter Valid Display Name", Snackbar.LENGTH_SHORT)
                        .setAction("action", null).show()
                }
                else -> {
                    val mp= HashMap<String,String>()
                    val mauth = FirebaseAuth.getInstance()
                    mp["first"] = enterFirstName.text.toString()
                    mp["last"] = enterLastName.text.toString()
                    mp["displayName"] = enterDisplayName.text.toString()
                    mp["mail"] = intent.getStringExtra("mail").toString()
                    val db = FirebaseFirestore.getInstance()
                    db.collection("desc").document(mauth.uid.toString()).set(mp).addOnSuccessListener {
                        Snackbar.make(save, "Data Saved", Snackbar.LENGTH_SHORT)
                            .setAction("action", null).show()

                        val intent = Intent(this, home::class.java)
                        startActivity(intent)
                        finish()

                    }.addOnFailureListener{
                        Snackbar.make(save, "error occured", Snackbar.LENGTH_SHORT)
                            .setAction("action", null).show()
                    }

                }
            }
        }
    }
}