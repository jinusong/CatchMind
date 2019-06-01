package com.jinwoo.catchmindandroid

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jinwoo.catchmindandroid.Model.GameData
import com.jinwoo.catchmindandroid.Util.SocketApplication
import io.socket.emitter.Emitter

class SubMainViewModel: ViewModel(){

    val socket = SocketApplication.socket

    val gameData = MutableLiveData<GameData>().apply { value = GameData }

    val answer = MutableLiveData<String>()
    val round = MutableLiveData<String>()
    val myScore = MutableLiveData<String>()
    val otherScore = MutableLiveData<String>()

    val word = MutableLiveData<String>()

    val mainChangeEvent = SingleLiveEvent<String>()
    val makeDialogEvent = SingleLiveEvent<String>()

    val wordData = Emitter.Listener { word.value = it.get(0).toString() }

    init {
        socket.emit("roundStart")
        socket.on("wordData", wordData)
        gameTextSetting()
    }

    fun answerCheck() {
        if(answer.value == word.value){
            socket.emit("pass")
            myWinRound()
        }
    }

    fun gameTextSetting() {
        this.round.value = "ROUND ${gameData.value!!.round}"
        this.myScore.value = gameData.value!!.myScore.toString()
        this.otherScore.value = gameData.value!!.otherScore.toString()
    }

    fun otherWinRound(){
        gameData.value!!.otherScore += 10
        gameData.value!!.round += 1
        endCheck()
    }

    fun myWinRound(){
        gameData.value!!.myScore += 10
        gameData.value!!.round += 1
        endCheck()
    }

    fun endCheck(){
        if(gameData.value!!.round > 5)
            makeDialogEvent.call()
        else mainChangeEvent.call()
    }
}