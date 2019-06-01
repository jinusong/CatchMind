package com.jinwoo.catchmindandroid.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.Util.SocketApplication
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.jetbrains.anko.startActivity

class ReadyActivity: AppCompatActivity(){

    val socket: Socket = SocketApplication.socket

    val start = Emitter.Listener { args ->
        if (args[0] as Boolean) startActivity<MainActivity>()
        else startActivity<SubMainActivity>()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)
        socket.connect()
    }

    override fun onStart() {
        super.onStart()
        socket.emit("ready")
        socket.on("start", start)
    }
}