package com.unravel.veza

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Timer : AppCompatActivity() {
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        title = "POMODORO"

        val startbt: FloatingActionButton = findViewById(R.id.floatingActionButton1)
        val pausebt: FloatingActionButton = findViewById(R.id.floatingActionButton)
        val stopbt: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        pausebt.visibility = View.GONE
        stopbt.visibility = View.GONE
        startbt.setOnClickListener{
            val enterMin: EditText = findViewById(R.id.editTextTime)
            val time = enterMin.text.toString()
            val target: Long = Integer.parseInt(time).toLong()
            val tex: TextView = findViewById(R.id.textView16)
            tex.text = target.toString()
            val i:TextView = findViewById(R.id.index)
            i.text = "asd"
            object: CountDownTimer(1000* 60 * target, 1000)
            {
                override fun onTick(millisUntilFinished: Long) {
                    val s: String = (counter/60).toString() + ":" + (counter%60).toString()
                    i.text = s
                    counter++
                }

                override fun onFinish() {
                    i.text = "FINISHED"
                }
            }.start()

            startbt.visibility = View.GONE
            pausebt.visibility = View.VISIBLE
            stopbt.visibility = View.VISIBLE


        }

    }

//    fun startTimer(view: View){
//        val countTime: TextView = findViewById(R.id.textView3)
//        object : CountDownTimer(1000 * 1800, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//             countTime.text = counter.toString()
//                counter++
//            }
//
//            override fun onFinish() {
//                countTime.text = getString(R.string.finished)
//            }
//
//        }.start()
//    }



}