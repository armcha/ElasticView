package io.armcha.sampleapp

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent


class ElasticView constructor(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    val EDGING = 10f
    val ANIMATION_DURATION = 200L

    val DEBUG_TAG = "HELLO"

    var cx = 0f
    var cy = 0f
    var isAnimating = false

    val gesture: GestureDetector

    init {
        val a: GestureDetector.SimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                Log.e(DEBUG_TAG, "onSingleTapConfirmed")
                return super.onSingleTapConfirmed(e)
            }
        }
        gesture = GestureDetector(context, a)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val verticalRotation = calculateRotation((event.x * EDGING * 2) / width)
        val horizontalRotation = -calculateRotation((event.y * EDGING * 2) / height)
        Log.e(DEBUG_TAG, "verticalRotation $verticalRotation")

        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                animate()
                        .rotationY(verticalRotation)
                        .rotationX(horizontalRotation)
                        .setDuration(ANIMATION_DURATION)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .withStartAction { isAnimating = true }
                        .withEndAction { isAnimating = false }
                        .start()
                cx = event.x
                cy = event.y
                Log.e(DEBUG_TAG, "Action was DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                rotationY = verticalRotation
                rotationX = horizontalRotation
                cx = event.x
                cy = event.y
                invalidate()
                Log.e(DEBUG_TAG, "Action was MOVE")
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                if (isAnimating) {
                    Handler().postDelayed({
                        animate()
                                .rotationX(0f)
                                .rotationY(0f)
                                .setDuration(ANIMATION_DURATION)
                                .setInterpolator(FastOutSlowInInterpolator())
                                .start()
                    }, ANIMATION_DURATION)
                    return super.onTouchEvent(event)
                }
                animate()
                        .rotationX(0f)
                        .rotationY(0f)
                        .setDuration(ANIMATION_DURATION)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .start()


                cx = -10f
                cy = -10f
                invalidate()
                Log.e(DEBUG_TAG, "Action was UP")
            }
        }
        return super.onTouchEvent(event)
    }

    private fun calculateRotation(value: Float): Float {
        var tempValue = when {
            value < 0 -> 1f
            value > EDGING * 2 -> EDGING * 2
            else -> value
        }
        tempValue -= EDGING
        return tempValue
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        val path = Path()
        path.moveTo(cx, 0f)
        path.lineTo(cx, height.toFloat())
        val pathSecond = Path()
        pathSecond.moveTo(0f, cy)
        pathSecond.lineTo(width.toFloat(), cy)
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.WHITE
            strokeWidth = 2f
            pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
        }
        canvas?.drawPath(path, paint)
        canvas?.drawPath(pathSecond, paint)
        canvas?.drawCircle(cx, cy, 15f, Paint().apply { style = Paint.Style.FILL;color = Color.WHITE })
    }
}