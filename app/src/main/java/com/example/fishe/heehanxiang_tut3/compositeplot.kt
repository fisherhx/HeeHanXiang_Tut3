package com.example.fishe.heehanxiang_tut3

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.SurfaceView
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_compositeplot.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.sin

class compositeplot :  Activity(), SeekBar.OnSeekBarChangeListener {

    companion object {

        lateinit var buffer: ShortArray
        lateinit var bufferCos: ShortArray
        lateinit var bufferCombine: ShortArray
        lateinit var bufferFreq: DoubleArray

        var bufferSize: Int = 1024     // in bytes
        var frequencySin: Double = 0.0
        var phaseSin: Double = 0.0
        var amplitudeSin: Double = 0.0

        var frequencyCos: Double = 0.0
        var phaseCos: Double = 0.0
        var amplitudeCos: Double = 0.0

        var fundamentalFrequency: Double = 0.0

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compositeplot)
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(intent)
        }
        val seekbarView1 = findViewById<SeekBar>(R.id.seekbar1)
        val seekbarView2 = findViewById<SeekBar>(R.id.seekbar2)
        val seekbarView3 = findViewById<SeekBar>(R.id.seekbar3)
        val seekbarViewCos1 = findViewById<SeekBar>(R.id.seekbarCos1)
        val seekbarViewCos2 = findViewById<SeekBar>(R.id.seekbarCos2)
        val seekbarViewCos3 = findViewById<SeekBar>(R.id.seekbarCos3)

        val surfaceView = findViewById<SurfaceView>(R.id.surfaceView)

        seekbarView1.max = 150
        seekbarView2.max = 300
        seekbarView3.max = 15

        seekbarViewCos1.max = 150
        seekbarViewCos2.max = 300
        seekbarViewCos3.max = 15

        seekbarView1.setOnSeekBarChangeListener(this)
        seekbarView2.setOnSeekBarChangeListener(this)
        seekbarView3.setOnSeekBarChangeListener(this)
        seekbarViewCos1.setOnSeekBarChangeListener(this)
        seekbarViewCos2.setOnSeekBarChangeListener(this)
        seekbarViewCos3.setOnSeekBarChangeListener(this)

        buffer = ShortArray(bufferSize)

    }
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if(seekBar.equals(seekbar1)){
            frequencySin = progress.toDouble()
        }
        if(seekBar.equals(seekbar2)){
            phaseSin = progress.toDouble()
        }
        if(seekBar.equals(seekbar3)){
            amplitudeSin = progress.toDouble()
        }
        if(seekBar.equals(seekbarCos1)){
            frequencyCos = progress.toDouble()
        }
        if(seekBar.equals(seekbarCos2)){
            phaseCos = progress.toDouble()
        }
        if(seekBar.equals(seekbarCos3)){
            amplitudeCos = progress.toDouble()
        }

        var tempFSin = frequencySin.toInt()
        var tempFCos = frequencyCos.toInt()
        fundamentalFrequency = GCD(tempFSin,tempFCos).toDouble()
        bufferFreq[0] = fundamentalFrequency

        when (seekBar.id) {
            R.id.seekbar1 -> textView1.setText(progress.toDouble().toString())
            R.id.seekbar2 -> textView2.setText(progress.toDouble().toString())
            R.id.seekbar3 -> textView3.setText(progress.toDouble().toString())

            R.id.seekbarCos1 -> textViewCos1.setText(progress.toDouble().toString())
            R.id.seekbarCos2 -> textViewCos2.setText(progress.toDouble().toString())
            R.id.seekbarCos3 -> textViewCos3.setText(progress.toDouble().toString())
        }
        var a:Double = amplitudeSin
        var b:Double = ((2*Math.PI/(1/ frequencySin))/ bufferSize)
        var c:Double = phaseSin*Math.PI / 180
        var result:Double = 0.0
        var resultFinal:Double = 0.0

        for (i in 0 until bufferSize) {
            result = a*Math.sin((b*i) + c)
            buffer[i] = result.toShort()
        }

        var aCos:Double = amplitudeCos
        var bCos:Double = ((2*Math.PI/(1/ frequencyCos))/ bufferSize)
        var cCos:Double = phaseCos*Math.PI / 180
        var resultCos:Double = 0.0

        for (i in 0 until bufferSize) {
            resultCos = aCos*Math.cos((bCos*i) + cCos)
            bufferCos[i] = resultCos.toShort()
        }

        for (i in 0 until bufferSize) {
            resultFinal = aCos*Math.cos((bCos*i) + cCos) + a*Math.sin((b*i) + c)
            bufferCombine[i] = resultFinal.toShort()
        }


    }
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    fun GCD(a: Int, b: Int): Int {
        return if (b == 0) a else GCD(b, a % b)
    }


}
