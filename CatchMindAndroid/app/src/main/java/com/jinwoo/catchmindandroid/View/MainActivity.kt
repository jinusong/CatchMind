package com.jinwoo.catchmindandroid.View

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.jinwoo.catchmindandroid.MainViewModel
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.Util.DrawClass
import com.jinwoo.catchmindandroid.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var timeCounter = 30
    var timeMinute = 1

    val drawClass: DrawClass by lazy { DrawClass(this) }

    val timerHandler = object: Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg!!.what == 1) timer.text = "$timeMinute:$timeCounter"
            else binding.mainViewModel!!.myWinRound()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = MainViewModel()
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawlayout.addView(drawClass)

        mainViewModel.clickedColor.observe(this, Observer {
            drawClass.setColor(Color.parseColor(it!!), 5f)
        })
        mainViewModel.eraserClickEvent.observe(this, Observer {
            drawClass.setColor(Color.parseColor("#FFFFFF"), 50f)
        })
        mainViewModel.subChangeEvent.observe(this, Observer {
            startActivity<SubMainActivity>()
            finish()
        })
        mainViewModel.makeDialogEvent.observe(this, Observer { makeDialog() })

        timer()

        binding.mainViewModel = mainViewModel
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
