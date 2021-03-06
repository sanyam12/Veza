package com.unravel.veza.ui.notes

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.unravel.veza.R
import com.unravel.veza.databinding.FragmentNotesBinding




private class MySampleFabFragment: AAH_FabulousFragment() {
    fun newInstance(): MySampleFabFragment? {
        return MySampleFabFragment()
    }

    override fun setupDialog(dialog: Dialog, style: Int)
    {
        val contentView: View = View.inflate(context,R.layout.fab_sample,null)
        val rl_content: RelativeLayout = contentView.findViewById(R.id.rl_content)
        val ll_buttons: LinearLayout = contentView.findViewById(R.id.ll_buttons)
        contentView.findViewById<Button>(R.id.btn_close).setOnClickListener {
            closeFilter(
                "closed"
            )
        }

        setViewMain(rl_content)
        setMainContentView(contentView)
        super.setupDialog(dialog, style)
    }
}


class NotesFragment : Fragment() {
    private lateinit var notesFragmentViewModel: NotesFragmentViewModel
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

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
        recyclerView.visibility = View.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter
        val userlist: ArrayList<PostDB> = arrayListOf()
        val adapter: PostAdapter = PostAdapter(userlist, view.context)
        recyclerView.adapter=adapter
        val db = FirebaseFirestore.getInstance()
        db.collection("notesCount").document("notes").get()
            .addOnSuccessListener {
                val data: Map<String, Map<String, String>> = it.data as Map<String, Map<String,String>>
                for(i in data.entries)
                {
//                    Toast.makeText(context, i.key, Toast.LENGTH_SHORT).show()
                    val item = PostDB(i.value["uid"].toString(), i.value["tittle"].toString(), i.value["author"].toString(), i.value["views"].toString(), i.value["i"].toString())
                    userlist.add(item)
                    adapter.notifyDataSetChanged()
                }
            }



//        for(i in 1..100)
//        {
//            val item: PostDB = PostDB("123", "name $i", "author $i", "1")
//            userlist.add(item)
//            adapter.notifyDataSetChanged()
//        }

        val newNotes: FloatingActionButton = view.findViewById(R.id.floatingActionButton3)
        newNotes.setOnClickListener{
//            val intent = Intent(activity, UploadNotes::class.java)
//            startActivity(intent)

            val dialogFrag: MySampleFabFragment? = MySampleFabFragment().newInstance()
            dialogFrag!!.setParentFab(newNotes)
            dialogFrag.show(requireActivity().supportFragmentManager, dialogFrag.getTag());

        }

    }

    private fun SelectPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).setType("application/pdf")
        val chooser = Intent.createChooser(intent, "Select PDF")
        startActivityForResult(chooser, 100)
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode==RESULT_OK)
        {
            uri = data?.data!!

        }
    }

    private fun UploadPdf() {
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
                db.collection("notesCount").document("number").update("count", i)
                Snackbar.make(requireActivity().findViewById(R.id.floatingActionButton3),
                    "file has been uploaded", Snackbar.LENGTH_SHORT )
                    .setAction("action", null).show()
            }.addOnProgressListener {
                Toast.makeText(activity, "uploading", Toast.LENGTH_SHORT)
            }.addOnFailureListener{
                Snackbar.make(requireActivity().findViewById(R.id.floatingActionButton3),
                    "error occured", Snackbar.LENGTH_SHORT )
                    .setAction("action", null).show()
            }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}