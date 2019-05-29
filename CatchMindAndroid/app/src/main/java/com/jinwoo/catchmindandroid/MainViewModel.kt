package com.jinwoo.catchmindandroid

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jinwoo.catchmindandroid.Model.PassModel
import com.jinwoo.catchmindandroid.Model.PlayerModel
import com.jinwoo.catchmindandroid.Model.SettingModel
import com.jinwoo.catchmindandroid.Util.Event

class MainViewModel: ViewModel(){

    val event = MutableLiveData<Event>()
    val playerModel = MutableLiveData<PlayerModel>()
    val settingModel = MutableLiveData<SettingModel>()
    val passModel = MutableLiveData<PassModel>()

    val clickedColor = MutableLiveData<String>()
    val eraserClickEvent = SingleLiveEvent<String>()

    val word = MutableLiveData<String>()
    val round = MutableLiveData<String>()
    val myScore = MutableLiveData<String>()
    val otherScore = MutableLiveData<String>()

    val subChangeEvent = SingleLiveEvent<String>()
    val makeDialogEvent = SingleLiveEvent<String>()

    init {
        gameObjectSetting()
        gameTextSetting()
        checkPass()
        event.value!!.receivePass()
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

    fun gameObjectSetting(){
        event.value = Event
        playerModel.value = PlayerModel
        settingModel.value = SettingModel
        passModel.value = PassModel
    }

    fun gameTextSetting() {
        word.value = playerModel.value!!.word

        this.round.value = "ROUND ${settingModel.value!!.round}"
        this.myScore.value = settingModel.value!!.myScore.toString()
        this.otherScore.value = settingModel.value!!.otherScore.toString()
    }

    fun checkPass(){ // 옵저빙 패턴이나 rxJava 넣으면 더 좋은 코드가 될 듯
        Thread{
            while(true){
                if(passModel.value!!.pass) {
                    endRound()
                    event.value!!.roundChange()
                    subChangeEvent.call()
                    endCheck()
                    break
                }
            }
        }
    }

    fun endRound(){
        settingModel.value!!.otherScore += 10
        settingModel.value!!.round += 1
    }

    fun endCheck(){
        if(settingModel.value!!.round > 5)
            makeDialogEvent.call()
        else subChangeEvent.call()
    }

    fun timesUp() {
        settingModel.value!!.round += 1
        event.value!!.roundChange()
        endCheck()
    }
}