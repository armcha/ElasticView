package io.armcha.elastic_view

import android.content.Context
import android.graphics.*
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class ElasticView constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private val ANIMATION_DURATION = 200L
    private val ANIMATION_DURATION_SHORT = 100L

    private var isAnimating = false
    private var isActionUpPerformed = false

    var flexibility = 5f
        set(value) {
            if (value !in 1f..15f) {
                throw IllegalArgumentException("Flexibility must be between [1f..15f].")
            }
            field = value
        }

    private var cx = 0f
    private var cy = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        log(flexibility.toString())
        val verticalRotation = calculateRotation((event.x * flexibility * 2) / width)
        val horizontalRotation = -calculateRotation((event.y * flexibility * 2) / height)

        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                isActionUpPerformed = false
                animate()
                        .rotationY(verticalRotation)
                        .rotationX(horizontalRotation)
                        .setDuration(ANIMATION_DURATION_SHORT)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .withStartAction { isAnimating = true }
                        .withEndAction {
                            if (isActionUpPerformed) {
                                animate()
                                        .rotationX(0f)
                                        .rotationY(0f)
                                        .setDuration(ANIMATION_DURATION)
                                        .setInterpolator(FastOutSlowInInterpolator())
                                        .start()
                            } else {
                                isAnimating = false
                            }
                        }
                        .start()
                cx = event.x
                cy = event.y
                log("Action was DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                rotationY = verticalRotation
                rotationX = horizontalRotation
                cx = event.x
                cy = event.y
                invalidate()
                log("Action was MOVE")
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                isActionUpPerformed = true
                if (!isAnimating) {
                    animate()
                            .rotationX(0f)
                            .rotationY(0f)
                            .setDuration(ANIMATION_DURATION)
                            .setInterpolator(FastOutSlowInInterpolator())
                            .start()

                }
                cx = -10f
                cy = -10f
                invalidate()
                log("Action was UP")
            }
        }
        return super.onTouchEvent(event)
    }

    private fun calculateRotation(value: Float): Float {
        var tempValue = when {
            value < 0 -> 1f
            value > flexibility * 2 -> flexibility * 2
            else -> value
        }
        tempValue -= flexibility
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

    private fun log(message: String) {
        Log.e("Elastic View ", message)
    }
}