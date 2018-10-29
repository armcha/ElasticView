package io.armcha.elasticview

import android.view.MotionEvent
import android.view.View

internal abstract class CentrePointProvider(protected val parentView: View) {

    protected var cx = -300f
    protected var cy = -300f

    init {
        parentView.setOnTouchListener { v, event ->
            attach(event)
            v.onTouchEvent(event)
        }
    }

    private fun attach(event: MotionEvent) {
        val (x, y) = if (event.action == MotionEvent.ACTION_MOVE) {
            event.x to event.y
        } else {
            -300f to -300f
        }
        cx = x
        cy = y
        parentView.invalidate()
    }
}