package com.jinwoo.catchmindandroid.View

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.SubMainViewModel
import com.jinwoo.catchmindandroid.Util.AutoDrawClass
import com.jinwoo.catchmindandroid.databinding.ActivityMainSubBinding
import kotlinx.android.synthetic.main.activity_main_sub.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.schedule

class SubMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainSubBinding

    var timeCounter = 30
    var timeMinute = 1

    val drawClass: AutoDrawClass by lazy { AutoDrawClass(this) }

    val timerHandler = object: Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg!!.what == 1) timer.text = "$timeMinute:$timeCounter"
            else if (msg!!.what == 2) binding.mainViewModel!!.otherWinRound()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val subMainViewModel = SubMainViewModel()
        binding = DataBindingUtil.setContentView<ActivityMainSubBinding>(this, R.layout.activity_main_sub)
        drawlayout.addView(drawClass)
        subMainViewModel.mainChangeEvent.observe(this, Observer {
            startActivity<MainActivity>()
            finish()
        })
        subMainViewModel.makeDialogEvent.observe(this, Observer { makeDialog() })

        timer()

        binding.mainViewModel = subMainViewModel
    }

    fun makeDialog() = EndDialog().show(supportFragmentManager, "game end")

    fun timer() {
        timer.text = "$timeMinute:$timeCounter"
        Timer().scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                timeCounter--
                if (timeCounter < 0) {
                    timeCounter = 59
                    timeMinute--
                }
                timerHandler.obtainMessage(1).sendToTarget()
                if (timeMinute == 0 && timeCounter == 0)
                    timerHandler.obtainMessage(2).sendToTarget()
            }
        }, 0 , 1000)
    }
}
