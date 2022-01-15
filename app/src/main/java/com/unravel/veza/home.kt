package com.unravel.veza
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.unravel.veza.databinding.ActivityHomeBinding

class home : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityHomeBinding

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityHomeBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

//        binding.appBarHome.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.profileFragment, R.id.notesFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val db = FirebaseFirestore.getInstance()
        val mauth = FirebaseAuth.getInstance()
        db.collection("desc").document("${mauth.currentUser?.uid}").get()
            .addOnSuccessListener {
                val mail = it.get("mail").toString()
                val name = it.get("first").toString()
                val last = it.get("last").toString()
                val storageReference = FirebaseStorage.getInstance().getReference()
                val file = storageReference.child("images/${mauth.currentUser?.uid}/profile_pic")
                val dp: ImageView = findViewById(R.id.nav_dp)
                val max_bytes:Long = 1024*1024
                file.getBytes(max_bytes).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                    dp.setImageBitmap(bitmap)
                }.addOnFailureListener{
                    dp.setImageResource(R.drawable.profile)
                    //Toast.makeText(this, "dp not found", Toast.LENGTH_SHORT).show()
                }

                val mailView: TextView = findViewById(R.id.textView)
                val nameView:TextView = findViewById(R.id.nav_name)
                mailView.text = mail
                nameView.text = name + " "+ last

            }
    }



}