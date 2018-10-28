package io.armcha.elastic_view

import android.graphics.*
import android.support.v4.content.ContextCompat
import android.view.View

class ShineProvider(parentView: View) : CentrePointProvider(parentView) {

    private val paint by lazy {
        Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
    }

    private val centreColor by lazy {
        ContextCompat.getColor(parentView.context, R.color.startColor)
    }
    private val shineRadius by lazy {
        parentView.height / 2.5f
    }

    fun onDispatchDraw(canvas: Canvas?) {
        paint.shader = RadialGradient(cx, cy, shineRadius, centreColor,
                Color.TRANSPARENT, Shader.TileMode.CLAMP)
        canvas?.drawCircle(cx, cy, shineRadius, paint)
    }
}