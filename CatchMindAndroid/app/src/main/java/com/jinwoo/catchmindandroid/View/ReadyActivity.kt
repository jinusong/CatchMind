package com.jinwoo.catchmindandroid.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jinwoo.catchmindandroid.Model.PlayerModel
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.Util.Event
import org.jetbrains.anko.startActivity

class ReadyActivity: AppCompatActivity(){

    val event by lazy { Event }
    val playerModel: PlayerModel by lazy { PlayerModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)
        event.start()
        startGame()
    }

    fun startGame() {
        if (playerModel.player){
            startActivity<MainActivity>()
            return
        }
        startActivity<SubMainActivity>()
    }

}