package com.unravel.veza.ui.pomo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.unravel.veza.BackPressHandler
import com.unravel.veza.R
import com.unravel.veza.Timer
class TimerGroupFragment: Fragment() {
//    private var _binding: FragmentGroupsBinding? = null
//    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(findNavController().currentDestination?.id == R.id.timerGroupFragment)
                    findNavController().navigate(R.id.action_timerGroupFragment_to_notesFragment)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}