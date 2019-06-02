package com.jinwoo.catchmindandroid.presenter

import com.jinwoo.catchmindandroid.contract.SubMainContract
import com.jinwoo.catchmindandroid.util.GameData
import com.jinwoo.catchmindandroid.util.SocketApplication
import io.socket.emitter.Emitter

class SubMainPresenter(val view: SubMainContract.View): SubMainContract.Presenter{

    val socket = SocketApplication.socket

    val gameData = GameData
    val wordData =  Emitter.Listener {
        gameData.word = it.get(0).toString()
        view.gameSetText(gameData)
    }

    override fun socketLogicSetting() {
        socket.emit("roundStart")
        socket.on("wordData", wordData)
    }

    override fun timeOut() = otherWinRound()

    override fun answerCheck(word: String) {
        if(gameData.word  == word){
            socket.emit("pass")
            myWinRound()
        }
    }

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
        else view.startMain()
    }
}