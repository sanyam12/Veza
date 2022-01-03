package com.unravel.veza.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unravel.veza.R
import com.unravel.veza.databinding.FragmentNotesBinding
import com.unravel.veza.databinding.FragmentProfileBinding
import com.unravel.veza.ui.profile.ProfileFragmentViewModel


class NotesFragment : Fragment() {
    private lateinit var notesFragmentViewModel: NotesFragmentViewModel
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

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


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}