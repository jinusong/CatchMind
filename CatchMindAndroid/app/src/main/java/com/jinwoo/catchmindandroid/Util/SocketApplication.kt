package com.jinwoo.catchmindandroid.Util

import android.app.Application
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketApplication: Application() {
    val socket: Socket = IO.socket("http://192.168.137.168:7000")
}