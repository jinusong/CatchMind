package com.jinwoo.catchmindandroid.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jinwoo.catchmindandroid.R
import dagger.android.support.DaggerAppCompatActivity
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class ReadyActivity: DaggerAppCompatActivity(){
    @Inject
    lateinit var socket: Socket

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