package com.jinwoo.catchmindandroid.View

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jinwoo.catchmindandroid.DialogViewModel
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.databinding.GgDialogBinding

class EndDialog: DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<GgDialogBinding>(inflater, R.layout.gg_dialog, container, false)
        val dialogViewModel = DialogViewModel()
        binding.dialogViewModel = dialogViewModel
        return binding.root
    }
}