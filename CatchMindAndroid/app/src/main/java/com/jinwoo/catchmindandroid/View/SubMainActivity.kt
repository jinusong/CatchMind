package com.jinwoo.catchmindandroid.View

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
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

    val drawClass: AutoDrawClass by lazy { AutoDrawClass(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainSubBinding>(this, R.layout.activity_main_sub)
        val subMainViewModel = SubMainViewModel()
        drawlayout.addView(drawClass)
        subMainViewModel.mainChangeEvent.observe(this, Observer {
            startActivity<MainActivity>()
            finish()
        })
        subMainViewModel.makeDialogEvent.observe(this, Observer { makeDialog() })

        timer(subMainViewModel)

        binding.mainViewModel = subMainViewModel
    }

    fun makeDialog() = EndDialog().show(supportFragmentManager, "game end")

    fun timer(viewModel: SubMainViewModel) {
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
                    runOnUiThread { viewModel.otherWinRound() }
                }
            }
        }
    }
}
