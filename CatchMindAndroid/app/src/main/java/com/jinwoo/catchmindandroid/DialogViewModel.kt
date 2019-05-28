package com.jinwoo.catchmindandroid

import android.arch.lifecycle.ViewModel
import com.jinwoo.catchmindandroid.Model.SettingModel
import com.jinwoo.catchmindandroid.Util.Event

class DialogViewModel: ViewModel(){

    val event by lazy { Event }
    val settingModel: SettingModel by lazy { SettingModel }

    var result: String = ""

    init {
        if (settingModel.myScore > settingModel.otherScore)
            result = "WIN!"
        else if (settingModel.myScore == settingModel.otherScore)
            result = "DRAW!"
        else
            result = "LOSE!"
    }

    fun endClick(){
        event.gameEnd()
        event.socket.disconnect()
        System.exit(0)
    }
}