package com.jinwoo.catchmindandroid.Util

import android.app.Application
import io.socket.client.IO

object SocketApplication: Application() {
    val socket = IO.socket("http://192.168.137.139:7000")
}