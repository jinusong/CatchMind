package com.jinwoo.catchmindandroid.View

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import com.jinwoo.catchmindandroid.DialogViewModel
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.databinding.GgDialogBinding

class EndDialog(context: Context): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.inflate<GgDialogBinding>(LayoutInflater.from(context), R.layout.gg_dialog, null, false)
        val dialogViewModel = DialogViewModel()
        binding.dialogViewModel = dialogViewModel
    }
}