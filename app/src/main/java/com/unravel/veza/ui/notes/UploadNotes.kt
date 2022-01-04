package com.unravel.veza.ui.notes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.unravel.veza.R

class UploadNotes:AppCompatActivity() {
    private lateinit var uri:Uri
    private var storageReference = FirebaseStorage.getInstance().getReference()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode== RESULT_OK)
        {
            uri = data?.data!!

        }
    }

    fun SelectPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).setType("application/pdf")
        val chooser = Intent.createChooser(intent, "Select PDF")
        startActivityForResult(chooser, 100)
    }

    fun UploadPdf() {
        val db = FirebaseFirestore.getInstance()
        var i: Int = 0
        db.collection("notesCount").document("number").get()
            .addOnSuccessListener {
                i = it.get("count").toString().toInt()
            }
        val id = FirebaseAuth.getInstance().currentUser?.uid
        val ref = storageReference.child("pdf/notes/$id/$i")
        i++
        ref.putFile(uri).addOnSuccessListener {
            Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_notes)

        val selectPdf: Button = findViewById(R.id.button4)
        val saveBt: Button = findViewById(R.id.button8)
        selectPdf.setOnClickListener{
            SelectPdf()
        }

        saveBt.setOnClickListener{
            UploadPdf()
        }

    }
}