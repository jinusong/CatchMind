package com.jinwoo.catchmindandroid

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jinwoo.catchmindandroid.Model.PlayerModel
import com.jinwoo.catchmindandroid.Model.SettingModel
import com.jinwoo.catchmindandroid.Util.SocketApplication
import io.socket.emitter.Emitter

class MainViewModel: ViewModel(){

    val settingModel = MutableLiveData<SettingModel>().apply { value = SettingModel }

    val clickedColor = MutableLiveData<String>()
    val eraserClickEvent = SingleLiveEvent<String>()

    val word = MutableLiveData<String>()
    val round = MutableLiveData<String>()
    val myScore = MutableLiveData<String>()
    val otherScore = MutableLiveData<String>()

    val subChangeEvent = SingleLiveEvent<String>()
    val makeDialogEvent = SingleLiveEvent<String>()

    val pass = Emitter.Listener { otherWinRound() }
    val drawData = Emitter.Listener { word.value = it.get(0).toString() }

    init {
        val socket = SocketApplication.socket

        socket.emit("drawer")
        socket.on("drawerData", drawData)
        socket.on("pass", pass)

        gameTextSetting()
    }

    fun colorClick(colorNum: Int) {
        clickedColor.value = when(colorNum) {
            0 -> "#FF8585"
            1 -> "#FF98C8"
            2 -> "#FFCE85"
            3 -> "#D81B60"
            4 -> "#FBFB8E"
            5 -> "#56E69E"
            6 -> "#ABF9F4"
            7 -> "#A4A7FF"
            8 -> "#9570FF"
            9 -> "#D784FF"
            10 -> "#757575"
            11 -> "#000000"
            else -> "#000000"
        }
    }

    fun eraserClick() = eraserClickEvent.call()

    fun gameTextSetting() {
        round.value = "ROUND ${settingModel.value!!.round}"
        myScore.value = settingModel.value!!.myScore.toString()
        otherScore.value = settingModel.value!!.otherScore.toString()
    }

    fun otherWinRound(){
        settingModel.value!!.otherScore += 10
        settingModel.value!!.round += 1
        endCheck()
    }

    fun myWinRound(){
        settingModel.value!!.myScore += 10
        settingModel.value!!.round += 1
        endCheck()
    }

    fun endCheck(){
        if(settingModel.value!!.round > 5)
            makeDialogEvent.call()
        else subChangeEvent.call()
    }
}