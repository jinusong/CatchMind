package com.jinwoo.catchmindandroid.util

import android.content.Context
import android.graphics.*
import android.view.View
import android.graphics.Bitmap
import android.util.Log
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject


class AutoDrawClass(context: Context) : View(context) {

    val socket: Socket by lazy { SocketApplication.socket }

    val paintColor: Int = Color.parseColor("#000000")

    var drawPath: Path? = Path()
    var drawPaint: Paint? = Paint()
    var canvasPaint: Paint? = null
    var drawCanvas: Canvas? = null
    var canvasBitmap: Bitmap? = null

    var actionDown = Emitter.Listener { args ->
        val jsonObject = args[0] as JSONObject
        Event(jsonObject.getDouble("x").toFloat()
            , jsonObject.getDouble("y").toFloat()
            , jsonObject.getInt("color")
            , jsonObject.getDouble("width").toFloat()
            , jsonObject.getString("eventName"))
    }

    init {
        setupDrawing()
        socket.on("action", actionDown)
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

    fun Event(touchX: Float, touchY: Float, color: Int, strokeWidth: Float,eventName: String){
        Log.e("fgdfgf", "$touchX, $touchY, $eventName")
        when (eventName) {
            "ACTION_DOWN" -> {
                drawPaint!!.color = color
                drawPaint!!.strokeWidth = strokeWidth
                drawPath!!.moveTo(touchX, touchY)
            }
            "ACTION_MOVE" -> {
                drawPaint!!.color = color
                drawPaint!!.strokeWidth = strokeWidth
                drawPath!!.lineTo(touchX, touchY)
            }
            "ACTION_UP" -> {
                drawPaint!!.color = color
                drawPaint!!.strokeWidth = strokeWidth
                drawPath!!.lineTo(touchX, touchY)
                drawCanvas!!.drawPath(drawPath, drawPaint)
                drawPath!!.reset()
            }
        }
    }

}