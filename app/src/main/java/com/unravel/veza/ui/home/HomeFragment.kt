package com.unravel.veza.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unravel.veza.Doubts
import com.unravel.veza.R
import com.unravel.veza.Timer
import com.unravel.veza.databinding.FragmentHomeBinding
import com.unravel.veza.ui.notes.NotesFragment
import com.unravel.veza.ui.profile.ProfileFragment

class HomeFragment : Fragment() {

  private lateinit var homeViewModel: HomeViewModel
private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    return root
  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val pomo: Button = view.findViewById(R.id.imageButton2)
//        pomo.setOnClickListener{
//            val intent = Intent(context, Timer::class.java)
//            startActivity(intent)
//        }

//        val notes:Button = view.findViewById(R.id.imageButton4)
//        notes.setOnClickListener{
//            val fragment: Fragment = NotesFragment()
//            val transaction: FragmentTransaction? = getFragmentManager()?.beginTransaction()
//            transaction!!.replace(R.id.notesFragment, fragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//
//        }

//        val doubts: Button = view.findViewById(R.id.imageButton5)
//        doubts.setOnClickListener{
//            val intent = Intent(context, Doubts::class.java)
//            startActivity(intent)
//        }

//        val profile: Button = view.findViewById(R.id.imageButton6)
//        profile.setOnClickListener{
//            val fragment: Fragment = ProfileFragment()
//            val transaction: FragmentTransaction? = getFragmentManager()?.beginTransaction()
//            transaction!!.replace(R.id.profileFragment, fragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }

    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}