package com.jinwoo.catchmindandroid.Util

import android.content.Context
import android.graphics.*
import android.view.View
import android.graphics.Bitmap
import android.util.Log
import com.jinwoo.catchmindandroid.Model.DrawModel


class AutoDrawClass(context: Context) : View(context) {

    val eventClass: Event by lazy { Event }
    val drawModel: DrawModel by lazy { DrawModel }

    val paintColor: Int = Color.parseColor("#000000")

    var drawPath: Path? = Path()
    var drawPaint: Paint? = Paint()
    var canvasPaint: Paint? = null
    var drawCanvas: Canvas? = null
    var canvasBitmap: Bitmap? = null
    var checked: Boolean = false

    init {
        setupDrawing()
        Thread{
            while (true) {
                checked = eventClass.receiveLine()
                if(checked){
                    drawPaint!!.color = drawModel.color
                    drawPaint!!.strokeWidth = drawModel.width
                    Event(drawModel.x, drawModel.y, drawModel.eventName)
                }
                checked = false
                // rxjava의 옵저빙 안써서 이렇게 했네요. 옵저버 패턴을 쓰기에는 애매해서...
            }
        }
    }

    fun setupDrawing() {
        drawPaint!!.color = paintColor
        drawPaint!!.isAntiAlias = true
        drawPaint!!.strokeWidth = 5f
        drawPaint!!.style = Paint.Style.STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)
        canvas.drawPath(drawPath, drawPaint)
    }

    fun Event(touchX: Float, touchY: Float, eventName: String?){
        Log.e("fgdfgf", "$touchX, $touchY, $eventName")
        when (eventName) {
            "ACTION_DOWN" -> {
                drawPath!!.moveTo(touchX, touchY)
            }
            "ACTION_MOVE" -> {
                drawPath!!.lineTo(touchX, touchY)
            }
            "ACTION_UP" -> {
                drawPath!!.lineTo(touchX, touchY)
                drawCanvas!!.drawPath(drawPath, drawPaint)
                drawPath!!.reset()
            }
            else -> false
        }

        invalidate()
        true
    }

}