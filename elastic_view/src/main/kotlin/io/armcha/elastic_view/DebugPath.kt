package io.armcha.elastic_view

import android.graphics.*
import android.view.MotionEvent
import android.view.View


class DebugPath(private val parentView: View) {

    private val pathPaint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            color = Color.WHITE
            strokeWidth = 2f
            pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
        }
    }
    private val circlePaint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }
    }
    private val horizontalPath = Path()
    private val verticalPath = Path()
    private var cx = -100f
    private var cy = -100f

    init {
        parentView.setOnTouchListener { v, event ->
            attach(event)
            v.onTouchEvent(event)
        }
    }

    private fun attach(event: MotionEvent) {
        val (x, y) = if (event.action == MotionEvent.ACTION_MOVE) {
            Pair(event.x, event.y)
        } else {
            Pair(-100f, -100f)
        }
        cx = x
        cy = y
        parentView.invalidate()
    }

    fun onDispatchDraw(canvas: Canvas?) {
        verticalPath.reset()
        horizontalPath.reset()

        horizontalPath.moveTo(cx, 0f)
        horizontalPath.lineTo(cx, parentView.height.toFloat())
        verticalPath.moveTo(0f, cy)
        verticalPath.lineTo(parentView.width.toFloat(), cy)
        canvas?.run {
            drawPath(horizontalPath, pathPaint)
            drawPath(verticalPath, pathPaint)
            drawCircle(cx, cy, 15f, circlePaint)
        }
    }

}