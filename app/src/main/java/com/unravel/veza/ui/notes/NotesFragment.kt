package com.unravel.veza.ui.notes

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.unravel.veza.R
import com.unravel.veza.databinding.FragmentNotesBinding
import com.unravel.veza.databinding.FragmentProfileBinding
import com.unravel.veza.ui.profile.ProfileFragmentViewModel


class NotesFragment : Fragment() {
    private lateinit var notesFragmentViewModel: NotesFragmentViewModel
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val pdf:Int = 0
    private lateinit var uri: Uri
    private var storageReference = FirebaseStorage.getInstance().getReference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notesFragmentViewModel =
            ViewModelProvider(this).get(NotesFragmentViewModel::class.java)

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_notes)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter
        val userlist: ArrayList<PostDB> = arrayListOf()
        val adapter: PostAdapter = PostAdapter(userlist, view.context)
        recyclerView.adapter=adapter
        for(i in 1..100)
        {
            val item: PostDB = PostDB("123", "name $i", "author $i", "1")
            userlist.add(item)
            adapter.notifyDataSetChanged()
        }

        val newNotes: FloatingActionButton = view.findViewById(R.id.floatingActionButton3)
        newNotes.setOnClickListener{
            SelectImage()
            UploadImage()
        }




    }
    private fun SelectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).setType("application/pdf")
        val chooser = Intent.createChooser(intent, "Select PDF")
        startActivityForResult(chooser, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode!=RESULT_OK)
        {
            uri = data?.data!!
        }
    }

    private fun UploadImage() {
        if(uri!=null)
        {
            val id = FirebaseAuth.getInstance().currentUser?.uid
            val ref = storageReference.child("notes/$id")


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}