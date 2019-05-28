package com.jinwoo.catchmindandroid.View

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.SubMainViewModel
import com.jinwoo.catchmindandroid.Util.AutoDrawClass
import com.jinwoo.catchmindandroid.databinding.ActivityMainSubBinding
import kotlinx.android.synthetic.main.activity_main_sub.*
import org.jetbrains.anko.startActivity

class SubMainActivity : AppCompatActivity() {

    val drawClass: AutoDrawClass by lazy { AutoDrawClass(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainSubBinding>(this, R.layout.activity_main_sub)
        val subMainViewModel = SubMainViewModel()
        drawlayout.addView(drawClass)
        subMainViewModel.mainChangeEvent.observe(this, Observer { startActivity<MainActivity>() })
        subMainViewModel.makeDialogEvent.observe(this, Observer { makeDialog() })
        binding.mainViewModel = subMainViewModel
    }

    fun makeDialog(){
        val dialog = EndDialog(this)
        var params: WindowManager.LayoutParams = dialog.window.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window.attributes = params
        dialog.show()
    }
}
