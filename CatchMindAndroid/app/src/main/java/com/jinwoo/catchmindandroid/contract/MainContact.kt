package com.jinwoo.catchmindandroid.contract

import com.jinwoo.catchmindandroid.util.GameData

interface MainContact {
    interface View {
        fun startSubMain()
        fun makeDialog()
        fun gameSetText(gameData: GameData)
    }
    interface Presenter {
        fun timeOut()
        fun socketLogicSetting()
    }
}