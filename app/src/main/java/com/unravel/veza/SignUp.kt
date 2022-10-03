package com.unravel.veza

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import androidx.core.app.ActivityCompat.startActivityForResult


class SignUp:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val backbt: ImageButton = findViewById(R.id.back)

        backbt.setOnClickListener{
            val intent: Intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }


        val signupbt: Button = findViewById(R.id.button)
        val enterMail: EditText = findViewById(R.id.editMail)
        val enterPass: EditText = findViewById(R.id.editPassword)
        val repPass: EditText = findViewById(R.id.editrepPassword)

        signupbt.setOnClickListener{
            when{

                TextUtils.isEmpty(enterMail.text.toString().trim{it<=' '}) -> {
                    Toast.makeText(
                        this@SignUp,
                        "Please enter Mail ID",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(enterPass.text.toString().trim{it<=' '})->{
                    Toast.makeText(
                        this,
                        "Please enter a password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !(TextUtils.equals(enterPass.text.toString(), repPass.text.toString()))->{
                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else ->{
                    val email: String = enterMail.text.toString().trim{it<=' '}
                    val password: String = enterPass.text.toString().trim{it<=' '}
                    val mauth = FirebaseAuth.getInstance()

                    mauth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> {task ->
                                if(task.isSuccessful)
                                {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(
                                        this,
                                        "You were registered successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this, signup_details::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("User_id", firebaseUser.uid)
                                    intent.putExtra("email-id", firebaseUser.email)
                                    startActivity(intent)
                                    finish()
                                } else{
                                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }

                            }


                        )
                }
            }
        }


    }

//    private fun signin()
//    {
//
//    }

}