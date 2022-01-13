package com.unravel.veza

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit

class PhoneLogin: AppCompatActivity() {
    private val mauth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    val callback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            //signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
//                Snackbar.make(findViewById(R.id.phone_text1), e.toString(), Snackbar.LENGTH_SHORT)
//                    .setAction("action", null).show()
            } else if (e is FirebaseTooManyRequestsException) {
//                Snackbar.make(findViewById(R.id.phone_text1), "SNS qouta reached", Snackbar.LENGTH_SHORT)
//                    .setAction("action", null).show()
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token
        }

    }


    fun isPhoneValid(no: String): Boolean {
        no.trim{it<=' '}
        if(no.isEmpty())
        {
            //Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            return false
        }

        for(i in no)
        {
            if(i.code<48)
            {
                //Toast.makeText(this, "loop", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        if(no.length==10)
        {
            return true
        }
        //Toast.makeText(this, "else", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_login)
        val phoneNo: EditText = findViewById(R.id.phone_text2)
        val otp: Button = findViewById(R.id.button9)


        otp.setOnClickListener{
            val no: String = phoneNo.text.toString()
            when{
                isPhoneValid(no)-> {
                    Snackbar.make(findViewById(R.id.phone_text1), "Phone No. valid... Sending OTP", Snackbar.LENGTH_SHORT)
                        .setAction("actions", null).show()
                    otp.isClickable = false
                    mauth.useAppLanguage()



                    val options = PhoneAuthOptions.newBuilder(mauth)
                       .setPhoneNumber("+91"+no)
                       .setTimeout(110L, TimeUnit.SECONDS)
                       .setActivity(this)
                       .setCallbacks(callback)
                       .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                    val enterOTP: EditText = findViewById(R.id.phone_text3)
                    val signIn: Button = findViewById(R.id.phone_signin)
                    signIn.setOnClickListener{
                        val otpText = enterOTP.text.toString()
                        val credential = PhoneAuthProvider.getCredential(storedVerificationId, otpText)
                        signInWithPhoneAuthCredential(credential)
                    }


                }else -> {
                    Toast.makeText(this, "Invalid Phone No...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mauth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    Toast.makeText(this, "logged in", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, home::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }


}