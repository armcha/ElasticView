package io.armcha.elastic_view

import android.graphics.*
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View


class DebugPath(parentView: View):CentrePointProvider(parentView) {

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