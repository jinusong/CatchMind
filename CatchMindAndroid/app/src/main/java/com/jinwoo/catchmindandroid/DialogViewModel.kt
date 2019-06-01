package com.jinwoo.catchmindandroid

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jinwoo.catchmindandroid.Model.GameData
import com.jinwoo.catchmindandroid.Util.SocketApplication
import io.socket.client.Socket

class DialogViewModel: ViewModel(){

    val socket: Socket by lazy { SocketApplication.socket }
    val settingModel: GameData by lazy { GameData }

    val myScore = MutableLiveData<String>().apply { GameData.myScore }
    val otherScore = MutableLiveData<String>().apply { GameData.otherScore }
    var result = MutableLiveData<String>()

    init {
        if (settingModel.myScore > settingModel.otherScore) result.value = "WIN!"
        else if (settingModel.myScore == settingModel.otherScore) result.value = "DRAW!"
        else result.value = "LOSE!"
    }

    fun endClick(){
        socket.disconnect()
        System.exit(0)
    }
}