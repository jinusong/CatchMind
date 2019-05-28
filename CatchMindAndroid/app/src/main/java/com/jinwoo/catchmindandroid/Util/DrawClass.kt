package com.jinwoo.catchmindandroid.Util

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.graphics.Bitmap

class DrawClass(context: Context) : View(context) {
    val eventClass: Event by lazy { Event }

    val paintColor: Int = Color.parseColor("#000000")
    var touchX = 0f
    var touchY = 0f

    var drawPath: Path? = Path()
    var drawPaint: Paint? = Paint()
    var canvasPaint: Paint? = null
    var drawCanvas: Canvas? = null
    var canvasBitmap: Bitmap? = null

    init { setupDrawing() }

    fun setColor(color: Int, width: Float){
        drawPaint!!.color = color
        drawPaint!!.strokeWidth = width
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchX = event.x
        touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                eventClass.sendLine(touchX, touchY, drawPaint!!.color, drawPaint!!.strokeWidth, "ACTION_DOWN")
                drawPath!!.moveTo(touchX, touchY)
            }
            MotionEvent.ACTION_MOVE -> {
                eventClass.sendLine(touchX, touchY, drawPaint!!.color, drawPaint!!.strokeWidth, "ACTION_MOVE")
                drawPath!!.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP -> {
                eventClass.sendLine(touchX, touchY, drawPaint!!.color, drawPaint!!.strokeWidth, "ACTION_UP")
                drawPath!!.lineTo(touchX, touchY)
                drawCanvas!!.drawPath(drawPath, drawPaint)
                drawPath!!.reset()
            }
            else -> return false
        }

        invalidate()
        return true
    }
}