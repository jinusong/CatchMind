package com.jinwoo.catchmindandroid.ui.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.util.GameData
import com.jinwoo.catchmindandroid.util.SocketApplication
import io.socket.client.Socket
import kotlinx.android.synthetic.main.gg_dialog.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class EndDialog: DialogFragment() {

    val socket: Socket = SocketApplication.socket
    val gameData = GameData


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.gg_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        game_close.onClick {
            socket.disconnect()
            System.exit(0)
        }
        searchResult()
    }

    fun searchResult() {
        myscore.text = gameData.myScore.toString()
        otherscore.text = gameData.otherScore.toString()
        result_tv.text =
            if (gameData.myScore > gameData.otherScore) "WIN!"
            else if (gameData.myScore == gameData.otherScore) "DRAW!"
            else "LOSE!"
    }
}