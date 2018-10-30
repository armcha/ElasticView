package io.armcha.sampleapp

import android.widget.SeekBar


/**
 *
 * Created by Arman Chatikyan on 29 Oct 2018
 *
 */

abstract class SeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

    abstract fun onProgress(progress: Int)

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onProgress(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }
}