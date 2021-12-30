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

                else->{
                    val email: String = enterMail.text.toString().trim{it<=' '}
                    val password: String = enterPass.text.toString().trim{it<=' '}
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener{
                            OnCompleteListener<AuthResult>{task->
                                if(task.isSuccessful) {
                                    val fireBaseUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(
                                        this,
                                        "You are now signed in",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent: Intent = Intent(this, PostAct::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    intent.putExtra("userid", fireBaseUser.uid)
                                    intent.putExtra("name", fireBaseUser.displayName)
                                    intent.putExtra("mail", fireBaseUser.email)
                                    startActivity(intent)
                                    finish()
                                }else
                                {
                                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }

                            }
                        }
                }
            }
        }
    }
}