package com.over.carameladskotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_act)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this,TestActivity::class.java))
            finish()
        },1500);
    }
}