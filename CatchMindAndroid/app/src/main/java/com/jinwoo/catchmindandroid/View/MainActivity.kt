package com.jinwoo.catchmindandroid.View

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
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

    val drawClass: DrawClass by lazy { DrawClass(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainViewModel = MainViewModel()
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

        timer(mainViewModel)

        binding.mainViewModel = mainViewModel
    }

    fun makeDialog() = EndDialog().show(supportFragmentManager, "game end")

    fun timer(viewModel: MainViewModel) {
        var timeCounter = 5
        var timeMinute = 0
        timer.text = "$timeMinute:$timeCounter"
        Timer("settingUp", false).schedule(1000) {
            while(true) {
                Thread.sleep(1000)
                timeCounter--
                if (timeCounter < 0) {
                    timeCounter = 59
                    timeMinute--
                }
                timer.text = "$timeMinute:$timeCounter"
                if (timeMinute == 0 && timeCounter == 0){
                    runOnUiThread { viewModel.myWinRound() }
                }
            }
        }
    }
}
