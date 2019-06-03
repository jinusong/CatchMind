package com.jinwoo.catchmindandroid.util

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket

object SocketApplication: Application() {
    val socket: Socket = IO.socket("http://172.30.1.153:7000")
}