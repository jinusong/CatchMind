package com.jinwoo.catchmindandroid.util

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket

object SocketApplication: Application() {
    val socket: Socket = IO.socket("http://52.78.209.85:7000")
}