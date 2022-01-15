package com.unravel.veza.ui.pomo

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.unravel.veza.R
import com.unravel.veza.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    private lateinit var timerFragment: TimerFragment
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(findNavController().currentDestination?.id == R.id.timerFragment)
                    findNavController().navigate(R.id.action_timerFragment_to_timerGroupFragment)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return  binding.root
    }

    fun isValidTime(time:String):Boolean{
//        for(i in timer)
//        {
//            if(i.code<48 && i.code>57)
//            {
//                return false
//            }
//        }
        if(time.isEmpty())
            return false
        if(time.contains(".")||time.contains(",")||time.contains("-")||time.contains(" "))
        {
            return false
        }
        return true
    }

    private fun timerPause(ti: t) {
        ti.timer.cancel()
    }

    private fun timerResume(ti: t) {
        ti.timer.start()
    }



    inner class t(var target: Long, i:TextView, var counter: Long) {
        val timer = object: CountDownTimer(1000* 60 * target, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //Toast.makeText(this@Timer, target.toString(), Toast.LENGTH_SHORT).show()
                target = millisUntilFinished
                val s: String = (counter/60).toString() + ":" + (counter%60).toString()
                i.text = s
                counter++
            }

            override fun onFinish() {

                i.text = getString(R.string.Finished)
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //title = "POMODORO"

        val startbt: FloatingActionButton = view.findViewById(R.id.floatingActionButton1)
        val pausebt: FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        val resumebt: FloatingActionButton = view.findViewById(R.id.floatingActionButton2)
        val stopbt: FloatingActionButton = view.findViewById(R.id.floatingActionButton4)
        pausebt.visibility = View.GONE
        resumebt.visibility = View.GONE
        stopbt.visibility = View.GONE
        startbt.setOnClickListener{
            val enterMin: EditText = view.findViewById(R.id.editTextTime)
            val time = enterMin.text.toString()
            if(!isValidTime(time))
            {
                Toast.makeText(activity, "Invalid time", Toast.LENGTH_SHORT).show()
            }
            else
            {
                var target = Integer.parseInt(time).toLong()
                val tex: TextView = view.findViewById(R.id.textView16)
                tex.text = target.toString()
                val i: TextView = view.findViewById(R.id.index)
                i.text = getString(R.string.asd)

//                val timer = object: CountDownTimer(1000* 60 * target, 1000) {
//                    override fun onTick(millisUntilFinished: Long) {
//                        //Toast.makeText(this@Timer, target.toString(), Toast.LENGTH_SHORT).show()
//                        target = millisUntilFinished
//                        val s: String = (counter/60).toString() + ":" + (counter%60).toString()
//                        i.text = s
//                        counter++
//                    }
//
//                    override fun onFinish() {
//
//                        i.text = getString(R.string.Finished)
//                    }
//                }
                val ti = t(target,i, counter = 0)
                ti.timer.start()


                startbt.visibility = View.GONE
                pausebt.visibility = View.VISIBLE
                stopbt.visibility = View.VISIBLE

                pausebt.setOnClickListener{
                    timerPause(ti)
                    resumebt.visibility = View.VISIBLE
                    pausebt.visibility = View.GONE
                }

                resumebt.setOnClickListener{
                    timerResume(ti)
                    resumebt.visibility = View.GONE
                    pausebt.visibility = View.VISIBLE
                }
                stopbt.setOnClickListener{
                    timerPause(ti)
                    target = 0
                    i.text = "START"
                    startbt.visibility = View.VISIBLE
                    pausebt.visibility = View.GONE
                    resumebt.visibility = View.GONE
                    stopbt.visibility = View.GONE
                }
            }


        }

        val groupbt: ImageButton = view.findViewById(R.id.imageButton7)
        groupbt.setOnClickListener{
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.timer_frame, TimerGroupFragment())
            transaction.addToBackStack("try")
            transaction.commit()

        }


    }



}