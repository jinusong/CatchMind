package com.jinwoo.catchmindandroid

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jinwoo.catchmindandroid.Model.PassModel
import com.jinwoo.catchmindandroid.Model.PlayerModel
import com.jinwoo.catchmindandroid.Model.SettingModel
import com.jinwoo.catchmindandroid.Util.Event

class SubMainViewModel: ViewModel(){

    val event = MutableLiveData<Event>()
    val playerModel = MutableLiveData<PlayerModel>()
    val settingModel = MutableLiveData<SettingModel>()
    val passModel = MutableLiveData<PassModel>()

    val answer = MutableLiveData<String>()
    val round = MutableLiveData<String>()
    val myScore = MutableLiveData<String>()
    val otherScore = MutableLiveData<String>()

    val mainChangeEvent = SingleLiveEvent<String>()
    val makeDialogEvent = SingleLiveEvent<String>()

    init {
        gameObjectSetting()
        gameTextSetting()
    }

    fun answerCheck() {
        if(answer.value == playerModel.value!!.word){
            endRound()
            event.value!!.sendPass()
            event.value!!.roundChange()
            mainChangeEvent.call()
            endCheck()
        }
    }

    fun gameObjectSetting(){
        event.value = Event
        playerModel.value = PlayerModel
        settingModel.value = SettingModel
        passModel.value = PassModel
    }

    fun gameTextSetting() {
        this.round.value = "ROUND ${settingModel.value!!.round}"
        this.myScore.value = settingModel.value!!.myScore.toString()
        this.otherScore.value = settingModel.value!!.otherScore.toString()
    }

    fun endRound(){
        settingModel.value!!.otherScore += 10
        settingModel.value!!.round += 1
    }

    fun endCheck(){
        if(settingModel.value!!.round > 5)
            makeDialogEvent.call()
        else mainChangeEvent.call()
    }

    fun timesUp() {
        settingModel.value!!.round += 1
        event.value!!.roundChange()
        endCheck()
    }
}