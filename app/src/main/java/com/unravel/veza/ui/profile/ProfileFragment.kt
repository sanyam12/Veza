package com.unravel.veza.ui.profile

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.unravel.veza.PostAct
import com.unravel.veza.R
import com.unravel.veza.databinding.FragmentProfileBinding
import com.unravel.veza.databinding.FragmentSlideshowBinding
import com.unravel.veza.home
import com.unravel.veza.login
import com.unravel.veza.ui.slideshow.SlideshowViewModel
import io.grpc.internal.SharedResourceHolder
import org.w3c.dom.Text
import java.io.File
import java.util.*
import android.graphics.BitmapFactory
import androidx.core.net.toFile


class ProfileFragment : Fragment() {
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var imageUri: Uri
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var storageReference = storage.getReference()
    private var isclick: Boolean = false
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        profileFragmentViewModel =
            ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dp: ImageView = view.findViewById(R.id.imageView5)
        val mauth = FirebaseAuth.getInstance()
        val dp_location = mauth.currentUser?.uid
        val url: String = "images/$dp_location/profile_pic"
        val max_bytes:Long = 1024*1024

        val imgRef = storageReference.child(url)
        imgRef.getBytes(max_bytes).addOnSuccessListener {
            val bitmap =BitmapFactory.decodeByteArray(it,0,it.size)
            dp.setImageBitmap(bitmap)
        }.addOnFailureListener{
            dp.setImageResource(R.drawable.profile)
        }




        val name: TextView = view.findViewById(R.id.textView5)


        val displayName: TextView = view.findViewById(R.id.textView11)
        val mail: TextView = view.findViewById(R.id.textView7)
        val db = FirebaseFirestore.getInstance()
        db.collection("desc").document(mauth.uid.toString()).get().addOnSuccessListener {
            var first = it.get("first").toString()
            var last = it.get("last").toString()
            var display = it.get("displayName").toString()
            val email = it.get("mail").toString()
            first+=" "+ last
            name.text = first
            displayName.text = display
            mail.text = email
        }


        val bt: Button = view.findViewById(R.id.button5)
        bt.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(activity, login::class.java)
            intent.putExtra("bool", "true")
            startActivity(intent)
        }

        val myButton: View = view.findViewById(R.id.imageButton3)
        myButton.setOnClickListener{
            SelectImage()
        }



        val save: Button = view.findViewById(R.id.button7)
        save.setOnClickListener{
            if(isclick)
                UploadImage()
            else
                Snackbar.make(view, "No changes to save,...", Snackbar.LENGTH_SHORT)
                    .setAction("action", null).show()
        }



    }

    private fun SelectImage() {
        val a: Intent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
        val chooser: Intent = Intent.createChooser(a,"Select image from here")
        startActivityForResult(chooser, 100)
        isclick=true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100&& resultCode== RESULT_OK){
            imageUri = data?.data!!
            _binding?.imageView5?.setImageURI(imageUri)

        }

    }

    private fun UploadImage(){
        if(imageUri!=null)
        {
            val id = FirebaseAuth.getInstance().currentUser?.uid
            val ref: StorageReference = FirebaseStorage.getInstance().getReference("images/$id/profile_pic")
            ref.putFile(imageUri)
                .addOnCompleteListener {
                        OnCompleteListener<UploadTask.TaskSnapshot>() {
                            Snackbar.make(requireView(), "success", Snackbar.LENGTH_INDEFINITE)
                                .setAction("action", null).show()
                    }
                }
                .addOnSuccessListener {
                    OnSuccessListener<UploadTask.TaskSnapshot>() {
                        Snackbar.make(requireView(), "success", Snackbar.LENGTH_INDEFINITE)
                            .setAction("action", null).show()
                    }
                }
                .addOnFailureListener{
                    OnFailureListener(){
                        Toast.makeText(activity, "Upload Failed ${it.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnProgressListener {
                    Toast.makeText(activity, "working on it", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}