package com.unravel.veza

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import kotlin.properties.Delegates

class Timer : AppCompatActivity() {
    var counter = 0
    private var milliLeft: Long = 0
    private var target by Delegates.notNull<Long>()
    private lateinit var i: TextView

    fun isValidTime(time:String):Boolean{
//        for(i in timer)
//        {
//            if(i.code<48 && i.code>57)
//            {
//                return false
//            }
//        }
        if(time.contains(".")||time.contains(",")||time.contains("-")||time.contains(" "))
        {
            return false
        }
        return true
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        title = "POMODORO"

        val startbt: FloatingActionButton = findViewById(R.id.floatingActionButton1)
        val pausebt: FloatingActionButton = findViewById(R.id.floatingActionButton)
        val resumebt: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        val stopbt: FloatingActionButton = findViewById(R.id.floatingActionButton4)
        pausebt.visibility = View.GONE
        resumebt.visibility = View.GONE
        stopbt.visibility = View.GONE
        startbt.setOnClickListener{
            val enterMin: EditText = findViewById(R.id.editTextTime)
            val time = enterMin.text.toString()
            if(!isValidTime(time))
            {
                Toast.makeText(this, "Invalid time, use Int type of minutes only", Toast.LENGTH_SHORT).show()
            }
            else
            {
                target = Integer.parseInt(time).toLong()
                var first: Boolean = false
                val tex: TextView = findViewById(R.id.textView16)
                tex.text = target.toString()
                i = findViewById(R.id.index)
                i.text = getString(R.string.asd)

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
                timer.start()

                startbt.visibility = View.GONE
                pausebt.visibility = View.VISIBLE
                stopbt.visibility = View.VISIBLE

                pausebt.setOnClickListener{
                    timerPause(timer)
                    resumebt.visibility = View.VISIBLE
                    pausebt.visibility = View.GONE
                }

                resumebt.setOnClickListener{
                    timerResume(timer)
                    resumebt.visibility = View.GONE
                    pausebt.visibility = View.VISIBLE
                }
                stopbt.setOnClickListener{
                    timerPause(timer)
                    target = 0
                    i.text = "START"
                    startbt.visibility = View.VISIBLE
                    pausebt.visibility = View.GONE
                    resumebt.visibility = View.GONE
                    stopbt.visibility = View.GONE
                }
            }


        }

    }


    private fun timerPause(timer: CountDownTimer) {
        timer.cancel()
    }

    private fun timerResume(timer: CountDownTimer) {
        timer.start()
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