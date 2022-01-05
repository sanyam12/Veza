package com.unravel.veza.ui.notes

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.unravel.veza.R
import com.unravel.veza.home
import java.io.File

class UploadNotes:AppCompatActivity() {
    private lateinit var uri:Uri
    private var storageReference = FirebaseStorage.getInstance().getReference()
    private var pdfName:String = "No File selected"

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode== RESULT_OK)
        {
            uri = data?.data!!
            if(uri.toString().startsWith("content://"))
            {
                var cursor: Cursor? = this.contentResolver.query(uri, null, null, null, null)
                if(cursor!=null&& cursor.moveToFirst())
                {
                    pdfName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                    val fileName:TextView = findViewById(R.id.textView22)
                    fileName.text = pdfName
                }
            }else if(uri.toString().startsWith("file://"))
            {
                val temp: File = File(uri.toString())
                pdfName = temp.name
                val fileName:TextView = findViewById(R.id.textView22)
                fileName.text = pdfName
            }
        }
    }

    fun SelectPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).setType("application/pdf")
        val chooser = Intent.createChooser(intent, "Select PDF")
        startActivityForResult(chooser, 100)
    }

    fun UploadPdf() {
        val db = FirebaseFirestore.getInstance()
        var i: Int = 123
        db.collection("notesCount").document("number").get()
            .addOnSuccessListener {
                i = it.get("count").toString().toInt()
                //Toast.makeText(this, "db get working $i", Toast.LENGTH_SHORT).show()

                val id = FirebaseAuth.getInstance().currentUser?.uid
                val ref = storageReference.child("pdf/notes/$id/$i")
                ref.putFile(uri!!).addOnSuccessListener {
                    db.collection("notesCount").document("number").update("count", (i+1))
                    Toast.makeText(this, "file has been uploaded", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, home::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Snackbar.make(findViewById(R.id.textView20), "Failed to Upload File $it", Snackbar.LENGTH_SHORT)
                        .setAction("action", null).show()
                }.addOnProgressListener {
                    while (it.task.isInProgress)
                        Snackbar.make(findViewById(R.id.textView20), "uploading you file", Snackbar.LENGTH_SHORT)
                            .setAction("action", null).show()
                }

                val file = HashMap<String, HashMap<String, String>>()
                val mauth = FirebaseAuth.getInstance()
                db.collection("desc").document("${mauth.currentUser?.uid.toString()}").get()
                    .addOnSuccessListener {
                        val name = it.get("displayName").toString()
                        val document = HashMap<String, HashMap<String, HashMap<String, String>>>()
                        val map =  HashMap<String, String>()
                        map["author"] = name
                        map["tittle"] = pdfName
                        map["uid"] = mauth.currentUser?.uid.toString()
                        map["views"] = "0"
                        map["i"] = i.toString()
                        file["$i"] = map
                        document["notes"] = file

//                        db.collection("notesCount").document("notes").set(file)
//                            .addOnSuccessListener {
//                                Toast.makeText(this, "firestore working", Toast.LENGTH_SHORT).show()
//                            }

                        db.collection("notesCount").document("notes").update(file as Map<String, Any>)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Firestore Updated", Toast.LENGTH_SHORT).show()
                            }

                    }


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
            if(uri==null)
            {
                Snackbar.make(findViewById(R.id.textView20), "Please select a file first", Snackbar.LENGTH_SHORT)
                    .setAction("action", null).show()
            }
            else
            {
                UploadPdf()
            }
        }

    }
}