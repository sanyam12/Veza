package com.unravel.veza

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignIn:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        val signinBt: Button = findViewById(R.id.button3)
        val enterMail: EditText = findViewById(R.id.signinEditMail)
        val enterPass: EditText = findViewById(R.id.signinEditPassword)
        signinBt.setOnClickListener{
            when{
                TextUtils.isEmpty(enterMail.text.toString().trim{it<=' '})-> {
                    Toast.makeText(
                        this,
                        "Enter mail please",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(enterPass.text.toString().trim{it<=' '})-> {
                    Toast.makeText(
                        this,
                        "Enter Password please",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else-> {
                    val mail : String = enterMail.text.toString()
                    val password :String = enterPass.text.toString()
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mail,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> {task->
                                if(task.isSuccessful)
                                {
                                    val firebaseUser:FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this, "LOGGED IN", Toast.LENGTH_SHORT).show()
                                    val intent = Intent (this, PostAct::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else
                                {
                                    Toast.makeText(this, task.exception!!.toString(), Toast.LENGTH_SHORT).show()
                                }

                            }
                        )
                }
            }
        }
    }
}