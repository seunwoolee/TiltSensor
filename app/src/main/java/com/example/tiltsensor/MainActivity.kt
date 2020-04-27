package com.example.tiltsensor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {

    private val mySensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private lateinit var tiltView: TiltView

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)

        tiltView = TiltView(this)
        setContentView(tiltView)
//        setContentView(R.layout.activity_main)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("MainActivity", "ddd")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //센서 값이 변경되면 호출
        // values[0] : x축
        // values[1] : y축
        // values[2] : z축
        event?.let {
            Log.d("MainActivity", "onSensorChanged: x: ${event.values[0]} y: ${event.values[1]}")
        }
        tiltView.onSensorEvent(event!!)

    }

    override fun onResume() {
        super.onResume()

        mySensorManager.registerListener(this,
            mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mySensorManager.unregisterListener(this)
    }

}
