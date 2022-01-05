package com.unravel.veza.ui.notes

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.google.firebase.storage.FirebaseStorage
import com.unravel.veza.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.logging.StreamHandler

class ViewNotes:AppCompatActivity() {
    val storageReference = FirebaseStorage.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_notespdf)

        val i: String = intent.getStringExtra("i").toString()
        val uid: String = intent.getStringExtra("uid").toString()

        val fileRef = storageReference.child("pdf/notes/$uid/$i")
        val fileName = intent.getStringExtra("title")
        val file = File(Environment.DIRECTORY_DOWNLOADS)
        fileRef.downloadUrl.addOnSuccessListener {
            val uri:String = it.toString()
            downloadFiles(this, fileName!!,".pdf", DIRECTORY_DOWNLOADS, uri)
        }.addOnFailureListener{
            Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show()
        }

    }

    private fun downloadFiles(context: Context, fileName:String,
                              fileExtension:String, destination:String,
                              url:String) {
        val downloadManager: DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(context,destination, fileName)
        downloadManager.enqueue(request)

    }
}