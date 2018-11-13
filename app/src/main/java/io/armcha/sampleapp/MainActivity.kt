package io.armcha.sampleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        seekBar.setOnSeekBarChangeListener(object : SeekBarChangeListener() {
            override fun onProgress(progress: Int) {
                imageElasticView.flexibility = progress / 10f + 1f
                seekBarText.text = "Flexibility is ${imageElasticView.flexibility}f"
            }
        })
        seekBar.progress = 40
    }
}