package com.unravel.veza.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.unravel.veza.R
import com.unravel.veza.databinding.FragmentProfileBinding
import com.unravel.veza.databinding.FragmentSlideshowBinding
import com.unravel.veza.login
import com.unravel.veza.ui.slideshow.SlideshowViewModel
import io.grpc.internal.SharedResourceHolder

class ProfileFragment : Fragment() {
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel
    private var _binding: FragmentProfileBinding? = null
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

        val id: Int = resources.getIdentifier("button5", "id", "com.unravel.veza.ui.profile")
        val v: View = requireView().findViewById(id)
        v.setOnClickListener{

        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val id: Int = resources.getIdentifier("button5", "id", "com.unravel.veza.ui.profile")
//        val v: View = requireView().findViewById(id)
//        v.setOnClickListener{
//            FirebaseAuth.getInstance().signOut()
//            val intent = Intent(activity, login::class.java)
//
//        }
//
//
//    }
}