package com.jinwoo.catchmindandroid.ui.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jinwoo.catchmindandroid.util.GameData
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.contract.SubMainContract
import com.jinwoo.catchmindandroid.presenter.SubMainPresenter
import com.jinwoo.catchmindandroid.util.AutoDrawClass
import com.jinwoo.catchmindandroid.ui.dialog.EndDialog
import kotlinx.android.synthetic.main.activity_main_sub.*
import org.jetbrains.anko.startActivity
import java.util.*

class SubMainActivity : AppCompatActivity(), SubMainContract.View {

    private lateinit var presenter: SubMainContract.Presenter

    var timeCounter = 30
    var timeMinute = 1

    val drawClass: AutoDrawClass by lazy { AutoDrawClass(this) }

    val timerHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg!!.what == 1) timer.text = "$timeMinute:$timeCounter"
            else if (msg.what == 2) presenter.timeOut()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sub)
        presenter = SubMainPresenter(this)
        presenter.socketLogicSetting()
        drawlayout.addView(drawClass)
        timer()
    }

    fun answerSubmitClick(v: View) = presenter.answerCheck(answer_edit.text.toString())

    override fun gameSetText(gameData: GameData) {
        runOnUiThread {
            round.text = "ROUND ${gameData.round}"
            myscore.text = gameData.myScore.toString()
            otherscore.text = gameData.otherScore.toString()
        }
    }

    override fun startMain() {
        runOnUiThread {
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun makeDialog() {
        runOnUiThread {
            EndDialog().show(supportFragmentManager, "game end")
        }
    }

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

    override fun onDestroy() {
        super.onDestroy()
        Timer().cancel()
    }
}
