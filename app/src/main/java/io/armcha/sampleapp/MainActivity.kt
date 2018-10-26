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

        elasticView.flexibility = 8f
//        elasticView.setOnClickListener {
//            Log.e("elasticView","Clicked")
//        }

        buttonElasticView.flexibility = 3f
        buttonElasticView.setOnClickListener {
            Log.e("buttonElasticView","Clicked")
            startActivity(Intent(this,RecyclerViewActivity::class.java))
        }
    }
}