package com.jinwoo.catchmindandroid

import android.arch.lifecycle.ViewModel

class StartViewModel: ViewModel() {

    val readyChangeEvent = SingleLiveEvent<String>()

    fun startClick() = readyChangeEvent.call()
}