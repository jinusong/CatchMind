package com.jinwoo.catchmindandroid.View

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.jinwoo.catchmindandroid.MainViewModel
import com.jinwoo.catchmindandroid.R
import com.jinwoo.catchmindandroid.Util.DrawClass
import com.jinwoo.catchmindandroid.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    val drawClass: DrawClass by lazy { DrawClass(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val mainViewModel = MainViewModel()
        drawlayout.addView(drawClass)

        mainViewModel.clickedColor.observe(this, Observer {
            drawClass.setColor(Color.parseColor(it!!), 5f)
        })
        mainViewModel.eraserClickEvent.observe(this, Observer {
            drawClass.setColor(Color.parseColor("#FFFFFF"), 50f)
        })
        mainViewModel.subChangeEvent.observe(this, Observer { startActivity<SubMainActivity>() })
        mainViewModel.makeDialogEvent.observe(this, Observer { makeDialog() })
        binding.mainViewModel = mainViewModel
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
