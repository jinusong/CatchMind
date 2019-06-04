package com.jinwoo.catchmindandroid.presenter

import com.jinwoo.catchmindandroid.contract.MainContact
import com.jinwoo.catchmindandroid.util.GameData
import com.jinwoo.catchmindandroid.util.SocketApplication
import io.socket.client.Socket
import io.socket.emitter.Emitter

class MainPresenter(val view: MainContact.View, val socket: Socket, val gameData: GameData): MainContact.Presenter {

    val pass = Emitter.Listener { otherWinRound() }
    val wordData =  Emitter.Listener {
        GameData.word = it.get(0).toString()
        view.gameSetText(gameData)
    }
    override fun socketLogicSetting() {
        socket.emit("roundStart")
        socket.on("wordData", wordData)
        socket.on("pass", pass)
    }

    override fun timeOut() = myWinRound()

    fun otherWinRound(){
        gameData.otherScore += 10
        gameData.round += 1
        endCheck()
    }

    fun myWinRound(){
        gameData.myScore += 10
        gameData.round += 1
        endCheck()
    }

    fun endCheck(){
        if(gameData.round > 5) view.makeDialog()
        else {
            socket.off()
            view.startSubMain()
        }
    }
}