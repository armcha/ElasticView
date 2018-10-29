package io.armcha.sampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //imageElasticView.flexibility = 8f
        imageElasticView.isDebugPathEnabled = true

        buttonElasticView.setOnClickListener {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageElasticView.flexibility = progress / 10f + 1f
                seekBarText.text = imageElasticView.flexibility.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        seekBar.progress = 40
    }
}