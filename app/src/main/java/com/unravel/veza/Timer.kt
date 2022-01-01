package com.unravel.veza

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class Timer : AppCompatActivity() {
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        title = "POMODORO"
    }

    fun startTimer(view: View){
        val countTime: TextView = findViewById(R.id.textView3)
        object : CountDownTimer(1000 * 1800, 1000) {
            override fun onTick(millisUntilFinished: Long) {
             countTime.text = counter.toString()
                counter++
            }

            override fun onFinish() {
                countTime.text = getString(R.string.finished)
            }

        }.start()
    }



}