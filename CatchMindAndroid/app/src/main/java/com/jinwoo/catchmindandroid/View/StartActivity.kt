package com.jinwoo.catchmindandroid.View

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.StartViewModel
import com.jinwoo.catchmindandroid.databinding.ActivityStartBinding
import org.jetbrains.anko.startActivity

class StartActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityStartBinding>(this, R.layout.activity_start)
        val startViewModel = StartViewModel()
        startViewModel.readyChangeEvent.observe(this, Observer {
            startActivity<ReadyActivity>()
        })
        binding.startViewModel = startViewModel
    }
}