package com.example.fishe.heehanxiang_tut3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            val intent = Intent(this, compositeplot::class.java).apply {
            }
            startActivity(intent)
        }
    }

/*    fun compositePlot(view: View) {
        val intent = Intent(this, compositeplot::class.java).apply {
        }
        startActivity(intent)
    }*/
}
