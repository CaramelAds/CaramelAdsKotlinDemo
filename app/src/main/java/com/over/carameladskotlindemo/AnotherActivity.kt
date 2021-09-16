package com.over.carameladskotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.caramelads.sdk.CaramelAds

class AnotherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.another_act)

        // back to main activity
        findViewById<View>(R.id.goBack).setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // call the caching method to reload the ad
        CaramelAds.cache(this@AnotherActivity)
    }
}