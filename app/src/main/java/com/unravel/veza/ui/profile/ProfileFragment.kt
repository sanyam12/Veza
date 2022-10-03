package com.unravel.veza.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.unravel.veza.R
import com.unravel.veza.databinding.FragmentProfileBinding
import com.unravel.veza.login
import android.graphics.BitmapFactory


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
            ViewModelProvider(this)[ProfileFragmentViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dp: ImageView = view.findViewById(R.id.imageView5)
        val mauth = FirebaseAuth.getInstance()
        val dp_location = mauth.currentUser?.uid
        val url = "images/$dp_location/profile_pic"
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
            selectImage()
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

    private fun selectImage() {
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
            val ref: StorageReference = storage.getReference("images/$id/profile_pic")
            ref.putFile(imageUri)
                .addOnSuccessListener {
                        Toast.makeText(activity, "Profile photo changed", Toast.LENGTH_SHORT).show()

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