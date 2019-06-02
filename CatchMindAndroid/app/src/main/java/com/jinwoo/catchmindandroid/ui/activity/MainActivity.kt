package com.jinwoo.catchmindandroid.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jinwoo.catchmindandroid.presenter.MainPresenter
import com.jinwoo.catchmindandroid.util.GameData
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.contract.MainContact
import com.jinwoo.catchmindandroid.util.DrawClass
import com.jinwoo.catchmindandroid.ui.dialog.EndDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity(), MainContact.View {

    private lateinit var presenter: MainContact.Presenter

    var timeCounter = 30
    var timeMinute = 1

    val drawClass: DrawClass by lazy { DrawClass(this) }

    val timerHandler = @SuppressLint("HandlerLeak")
    object: Handler() {
        override fun handleMessage(msg: Message?) {
            if (msg!!.what == 1) timer.text = "$timeMinute:$timeCounter"
            else presenter.timeOut()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        presenter.socketLogicSetting()
        drawlayout.addView(drawClass)
        timer()
    }

    fun redColorClick(v: View) = drawClass.setColor(Color.parseColor("#FF8585"), 5f)

    fun pinkColorClick(v: View) = drawClass.setColor(Color.parseColor("#FF98C8"), 5f)

    fun orangeColorClick(v: View) = drawClass.setColor(Color.parseColor("#FFCE85"), 5f)

    fun deepRedColorClick(v: View) = drawClass.setColor(Color.parseColor("#D81B60"), 5f)

    fun yellowColorClick(v: View) = drawClass.setColor(Color.parseColor("#FBFB8E"), 5f)

    fun greenColorClick(v: View) = drawClass.setColor(Color.parseColor("#56E69E"), 5f)

    fun skyColorClick(v: View) = drawClass.setColor(Color.parseColor("#ABF9F4"), 5f)

    fun lightPurpleColorClick(v: View) = drawClass.setColor(Color.parseColor("#A4A7FF"), 5f)

    fun purpleColorClick(v: View) = drawClass.setColor(Color.parseColor("#9570FF"), 5f)

    fun amethystColorClick(v: View) = drawClass.setColor(Color.parseColor("#D784FF"), 5f)

    fun grayColorClick(v: View) = drawClass.setColor(Color.parseColor("#757575"), 5f)

    fun blackColorClick(v: View) = drawClass.setColor(Color.parseColor("#000000"), 5f)

    fun eraserClick(v: View) = drawClass.setColor(Color.parseColor("#FFFFFF"), 50f)

    override fun startSubMain() {
        runOnUiThread {
            startActivity<SubMainActivity>()
            finish()
        }
    }

    override fun makeDialog() {
        runOnUiThread {
            EndDialog().show(supportFragmentManager, "game end")
        }
    }

    override fun gameSetText(gameData: GameData) {
        runOnUiThread {
            word.text = gameData.word
            round.text = "ROUND ${gameData.round}"
            myscore.text = gameData.myScore.toString()
            otherscore.text = gameData.otherScore.toString()
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
}
