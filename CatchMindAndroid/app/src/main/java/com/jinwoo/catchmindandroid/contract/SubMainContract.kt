package com.jinwoo.catchmindandroid.contract

import com.jinwoo.catchmindandroid.util.GameData

interface SubMainContract {
    interface View {
        fun startMain()
        fun makeDialog()
        fun gameSetText(gameData: GameData)
    }

    interface Presenter {
        fun timeOut()
        fun answerCheck(word: String)
        fun socketLogicSetting()
    }
}