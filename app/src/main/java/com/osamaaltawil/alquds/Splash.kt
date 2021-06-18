package com.osamaaltawil.alquds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        //window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val back=object :Thread(){
            override fun run() {
                sleep(1000)
                    startActivity(Intent(applicationContext, Navigation_Activity::class.java))
                    finish()

                super.run()
            }
        }
        back.start()
    }
}