package com.unravel.veza

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.unravel.veza.databinding.FragmentGroupsBinding

class TimerGroupFragment: Fragment(R.layout.fragment_groups) {
//    private var _binding: FragmentGroupsBinding? = null
//    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back: ImageButton = view.findViewById(R.id.imageButton8)
        back.setOnClickListener{
            val fragment: Fragment = FragmentManager.findFragment(back)
            val manager: FragmentManager = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            val intent = Intent(activity, Timer::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
//            transaction.replace(R.id.timer_frame, )
//            transaction.addToBackStack(null)
//            transaction.commit()
        }

    }


}