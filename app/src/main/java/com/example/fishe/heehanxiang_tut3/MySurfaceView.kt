package com.example.fishe.heehanxiang_tut3


import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.fishe.heehanxiang_tut3.compositeplot.Companion.buffer
import com.example.fishe.heehanxiang_tut3.compositeplot.Companion.bufferSize
import com.example.fishe.heehanxiang_tut3.compositeplot.Companion.bufferCos
import com.example.fishe.heehanxiang_tut3.compositeplot.Companion.bufferCombine
import com.example.fishe.heehanxiang_tut3.compositeplot.Companion.bufferFreq

import java.util.concurrent.Executors

/**
 * Created by ngtk on 29/1/18.
 */
class MySurfaceView : SurfaceView, SurfaceHolder.Callback {

    @Volatile var drawFlag = false

    val executor = Executors.newSingleThreadScheduledExecutor()

    private var x : Int = 0
    private var y : Int = 0

    private var line_width : Float = 6f


    /***
    private var Paint1: Paint? = null
    private var Paint2: Paint? = null
    private var Paint3: Paint? = null
     ***/

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }


    override fun surfaceCreated(p0: SurfaceHolder?) {

        start()

    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

        drawFlag = false

        executor.shutdownNow()

    }


    fun initialize() {
        holder.addCallback(this)


        /*****
        Paint1 = Paint()
        Paint1!!.setARGB(255, 0, 0, 255)
        Paint1!!.strokeWidth = 3f

        Paint2 = Paint()
        Paint2!!.isAntiAlias = true
        Paint2!!.setARGB(255, 255, 0, 0)
        Paint2!!.strokeWidth = 4f

        Paint3 = Paint()
        Paint3!!.isAntiAlias = true
        Paint3!!.setARGB(255, 0, 255, 255)
        Paint3!!.strokeWidth = 3f
         *****/

        buffer = ShortArray(bufferSize)
        bufferCos = ShortArray(bufferSize)
        bufferCombine = ShortArray(bufferSize)
        bufferFreq = DoubleArray(bufferSize)

        drawFlag = true

    }


    fun draw() {

        if (drawFlag) {


            val c = holder.lockCanvas()

            c.drawColor(Color.LTGRAY)       // background color

            val p = Paint()

            //p.setARGB(255, 255, rand(0, 128), rand(0, 255))
            p.setARGB(255, 255, 0, 0)
            p.setStrokeWidth(line_width)

            val cosP = Paint()

            cosP.setARGB(255, 0, 0, 255)
            cosP.setStrokeWidth(line_width)

            val combineP = Paint()
            combineP.setARGB(255, 0, 0, 0)
            combineP.setStrokeWidth(line_width)

            val yOffset = 300.0f
            val yScale = 30.0f

            var fundamentalFreq = bufferFreq[0].toFloat()
            //fundamentalFreq = ((2*Math.PI/(1/ fundamentalFreq))/ bufferSize).toFloat()
            var fundamentalPeriod:Float = (1/ fundamentalFreq) * 1024.0f

            var prevX = 0.0f
            var prevY: Float = buffer[0].toFloat() * yScale
            var tempX = 0.0f
            var tempY: Float = prevY

            for (i in 1..bufferSize - 1) {
                tempX += 1

                tempY = buffer[i].toFloat() * yScale

                c.drawLine(prevX, prevY + yOffset, tempX, tempY + yOffset, p)

                prevX = tempX
                prevY = tempY
            }

            prevX = 0.0f
            prevY = bufferCos[0].toFloat() * yScale
            tempX = 0.0f
            tempY = prevY

            for (i in 1..bufferSize - 1) {
                tempX += 1

                tempY = bufferCos[i].toFloat() * yScale

                c.drawLine(prevX, prevY + yOffset, tempX, tempY + yOffset, cosP)

                prevX = tempX
                prevY = tempY

            }

            prevX = 0.0f
            prevY = bufferCombine[0].toFloat() * yScale
            tempX = 0.0f
            tempY = prevY

            for (i in 1..bufferSize - 1) {
                tempX += 1

                tempY = bufferCombine[i].toFloat() * yScale

                c.drawLine(prevX, prevY + yOffset, tempX, tempY + yOffset, combineP)


                prevX = tempX
                prevY = tempY

            }

            c.drawLine(fundamentalPeriod, -1024.0f + yOffset, fundamentalPeriod, 1024.0f + yOffset, cosP)

            holder.unlockCanvasAndPost(c)

        }
    }

    fun  start() {

        executor.scheduleAtFixedRate( {
            draw()
        }, 100, 10, java.util.concurrent.TimeUnit.MILLISECONDS )        // initial delay 100, period 10

    }


    /*****

    fun rand(from: Int, to: Int) : Int {
    return Random().nextInt(to-from) + from
    }
     ****/

    /*****
    override fun onTouchEvent(event: MotionEvent): Boolean {
    val a = event.action

    x = event.x.toInt()
    y = event.y.toInt()


    when (a) {
    MotionEvent.ACTION_DOWN -> line_width = 60f
    MotionEvent.ACTION_MOVE -> line_width = 10f
    }
    return true
    }
     ****/

}