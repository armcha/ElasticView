package io.armcha.elastic_view

import android.content.Context
import android.graphics.Canvas
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

class ElasticView constructor(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    private val ANIMATION_DURATION = 200L
    private val ANIMATION_DURATION_SHORT = 100L

    private var isAnimating = false
    private var isActionUpPerformed = false

    private val debugPath by lazy {
        DebugPath(this)
    }

    var flexibility = 5f
        set(value) {
            if (value !in 1f..15f) {
                throw IllegalArgumentException("Flexibility must be between [1f..15f].")
            }
            field = value
        }

    init {
        isClickable = true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        processTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        processTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private fun processTouchEvent(event: MotionEvent) {
        val verticalRotation = calculateRotation((event.x * flexibility * 2) / width)
        val horizontalRotation = -calculateRotation((event.y * flexibility * 2) / height)

        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                animate().apply {
                    rotationY(verticalRotation)
                    rotationX(horizontalRotation)
                    duration = ANIMATION_DURATION_SHORT
                    interpolator = FastOutSlowInInterpolator()
                    withStartAction {
                        isActionUpPerformed = false
                        isAnimating = true
                    }
                    withEndAction {
                        if (isActionUpPerformed) {
                            animate().apply {
                                rotationX(0f)
                                rotationY(0f)
                                duration = ANIMATION_DURATION
                                interpolator = FastOutSlowInInterpolator()
                                start()
                            }
                        } else {
                            isAnimating = false
                        }
                    }
                    start()
                }
                log("Action was DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                rotationY = verticalRotation
                rotationX = horizontalRotation
                log("Action was MOVE")
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                isActionUpPerformed = true
                if (!isAnimating) {
                    animate().apply {
                        rotationX(0f)
                        rotationY(0f)
                        duration = ANIMATION_DURATION
                        interpolator = FastOutSlowInInterpolator()
                        start()
                    }
                }
                log("Action was UP")
            }
        }
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
        debugPath.onDispatchDraw(canvas)
    }

    private fun log(message: String) {
        Log.e("Elastic View ", message)
    }
}