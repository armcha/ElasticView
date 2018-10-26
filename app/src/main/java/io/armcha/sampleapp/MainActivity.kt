package io.armcha.sampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageElasticView.flexibility = 8f

        buttonElasticView.setOnClickListener {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }
    }
}