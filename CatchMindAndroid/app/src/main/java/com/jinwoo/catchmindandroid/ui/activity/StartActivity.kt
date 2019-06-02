package com.jinwoo.catchmindandroid.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jinwoo.catchmindandroid.R
import org.jetbrains.anko.startActivity

class StartActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun startClick(v: View) = startActivity<ReadyActivity>()
}