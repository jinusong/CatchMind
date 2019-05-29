package com.jinwoo.catchmindandroid.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jinwoo.catchmindandroid.Model.PlayerModel
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.Util.Event
import com.jinwoo.catchmindandroid.Util.SocketApplication
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.jetbrains.anko.startActivity

class ReadyActivity: AppCompatActivity(){

    val playerModel: PlayerModel by lazy { PlayerModel }

    val start = Emitter.Listener {
        if (playerModel.player) startActivity<MainActivity>()
        else startActivity<SubMainActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)
        val socket: Socket = SocketApplication.socket
        socket.emit("ready")
        socket.on("start", start)
    }
}