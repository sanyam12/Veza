package com.unravel.veza

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.unravel.veza.ui.pomo.TimerGroupFragment

class MySampleFabFragment: AAH_FabulousFragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val test: Button = view.findViewById(R.id.button12)
        test.setOnClickListener{
            Toast.makeText(activity, "this", Toast.LENGTH_SHORT).show()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.homeFloating, TimerGroupFragment())
                .addToBackStack("test")
                .commit()
        }
    }
}

class FloatingTimer: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.floating_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bt: FloatingActionButton = view.findViewById(R.id.floatingActionButton5)
        bt.setOnClickListener{
            Toast.makeText(activity, "This is working", Toast.LENGTH_SHORT).show()
            val dialogFrag: MySampleFabFragment? = MySampleFabFragment().newInstance()
            dialogFrag!!.setParentFab(bt)
            dialogFrag.show(requireActivity().supportFragmentManager, dialogFrag.getTag())
        }

    }
}