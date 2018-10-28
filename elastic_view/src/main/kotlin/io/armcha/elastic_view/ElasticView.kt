package io.armcha.elastic_view

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewPropertyAnimator


class ElasticView(context: Context, attrs: AttributeSet? = null) : CardView(context, attrs) {

    private val ANIMATION_DURATION = 200L
    private val ANIMATION_DURATION_SHORT = 100L

    private var isAnimating = false
    private var isActionUpPerformed = false

    private val debugPath by lazy {
        DebugPath(this)
    }
    private val shineProvider by lazy {
        ShineProvider(this)
    }

    var flexibility = 5f
        set(value) {
            if (value !in 1f..15f) {
                throw IllegalArgumentException("Flexibility must be between [1f..15f].")
            }
            field = value
        }

    var isDebugPathEnabled = false
    var isShineEnabled = false

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

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if (isDebugPathEnabled)
            debugPath.onDispatchDraw(canvas)
        if (isShineEnabled)
            shineProvider.onDispatchDraw(canvas)
    }

    private fun processTouchEvent(event: MotionEvent) {
        val verticalRotation = calculateRotation((event.x * flexibility * 2) / width)
        val horizontalRotation = -calculateRotation((event.y * flexibility * 2) / height)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                animator {
                    rotationY(verticalRotation)
                    rotationX(horizontalRotation)
                    duration = ANIMATION_DURATION_SHORT
                    withStartAction {
                        isActionUpPerformed = false
                        isAnimating = true
                    }
                    withEndAction {
                        if (isActionUpPerformed) {
                            animateToOriginalPosition()
                        } else {
                            isAnimating = false
                        }
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                rotationY = verticalRotation
                rotationX = horizontalRotation
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                isActionUpPerformed = true
                if (!isAnimating) {
                    animateToOriginalPosition()
                }
            }
        }
    }

    private fun animator(body: ViewPropertyAnimator.() -> Unit) {
        animate().apply {
            interpolator = FastOutSlowInInterpolator()
            body()
            start()
        }
    }

    private fun animateToOriginalPosition() {
        animator {
            rotationX(0f)
            rotationY(0f)
            duration = ANIMATION_DURATION
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
}