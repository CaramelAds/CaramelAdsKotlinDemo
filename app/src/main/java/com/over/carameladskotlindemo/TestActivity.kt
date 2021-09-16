package com.over.carameladskotlindemo

import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.animation.*
import android.widget.*
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.caramelads.sdk.CaramelAdListener
import com.caramelads.sdk.CaramelAds

class TestActivity : AppCompatActivity() {
    private var rootLayout: RelativeLayout? = null
    private var screenSize: Point? = null
    private var button: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test)

        //inittialize Caramel ADS
        CaramelAds.initialize(this@TestActivity)

        // event listener. You can set your own actions in response to events
        CaramelAds.setAdListener(object : CaramelAdListener {
            override fun sdkReady() {
                showToast("SDK READY", "sdk is ready, wait while ad is load to cache and Caramel button is enable")
                //cache ads after CaramelSDK is ready
                CaramelAds.cache(this@TestActivity)
            }

            override fun sdkFailed() {
                showToast("SDK FAILED", "sdk is failed")
            }

            override fun adLoaded() {
                showToast("AD LOADED", "ad is loaded and you can push the Caramel button")
            }

            override fun adOpened() {
                showToast("AD OPENED", "ad is opened")
            }

            override fun adClicked() {
                showToast("AD CLICKED", "clicked on ad")
            }

            override fun adClosed() {
                showToast("AD CLOSED", "ad is closed")
            }

            override fun adFailed() {
                showToast("AD FAILED", "ad is failed")
            }
        })

        initRes();
        val itemWidth = screenSize!!.x / 5
        val itemHeight = screenSize!!.y / 10

        val title = initImage(
            (itemWidth * 3),
            (itemHeight * 2.5).toInt(), R.drawable.logo
        )
        title.translationX = (screenSize!!.x / 2 - (itemWidth * 3) as Int / 2).toFloat()
        title.translationY = (screenSize!!.y / 9).toFloat()
        val aan = AlphaAnimation(0f, 1f)
        aan.duration = 2000
        title.animation = aan
        rootLayout!!.addView(title)

        initCloud(itemWidth, itemHeight, R.drawable.cloud, 1000, -itemWidth.toFloat(), (screenSize!!.x / 6).toFloat(), 0f, 0f)
        initCloud(itemWidth, itemHeight, R.drawable.cloud, 1400, screenSize!!.x.toFloat(), screenSize!!.x / 1.4f, 0f, 0f)
        initCloud(itemWidth, itemHeight, R.drawable.cloud, 2000, -itemWidth.toFloat(), (screenSize!!.x / 6).toFloat(), screenSize!!.y / 2.8f, screenSize!!.y / 2.8f)

        button = initImage(itemWidth, itemWidth, R.drawable.button)
        button!!.startAnimation(initButtonAnimation())
        button!!.setTranslationX(screenSize!!.x / 2 - itemWidth / 2.5f)
        button!!.setTranslationY(screenSize!!.y / 3.8f)
        rootLayout!!.addView(button)

        // launch new activity on click to button at the center of screen
        button!!.setOnClickListener {
            // show caramel ads if is loaded
            if (CaramelAds.isLoaded()) {
                startActivity(Intent(this, AnotherActivity::class.java))
                CaramelAds.show()
            } else showToast("WAIT", "wait while ad is load to cache and Caramel button is enable")
        }
    }

    private fun initCloud(width: Int, height: Int, id: Int, duration: Int, fromDX: Float, toDX: Float, fromDY: Float, toDY: Float) {
        val cloud: ImageView = initImage(width, height, id)
        cloud.animation = initTranslateAnimation(fromDX, toDX, fromDY, toDY, duration)
        rootLayout!!.addView(cloud)
    }

    private fun initRes() {
        rootLayout = findViewById(R.id.rootLayout)
        screenSize = Point()
        windowManager.defaultDisplay.getSize(screenSize)
    }

    private fun initImage(with: Int, height: Int, id: Int): ImageView {
        val img = ImageView(this)
        img.setBackgroundResource(id)
        val layoutParams = LinearLayout.LayoutParams(with, height)
        img.layoutParams = layoutParams
        return img
    }

    private fun initTranslateAnimation(from_dx: Float, to_dx: Float, from_dy: Float, to_dy: Float, duration: Int): TranslateAnimation? {
        val tr = TranslateAnimation(from_dx, to_dx, from_dy, to_dy)
        tr.duration = duration.toLong()
        tr.interpolator = LinearOutSlowInInterpolator()
        tr.fillAfter = true
        return tr
    }

    private fun initButtonAnimation(): ScaleAnimation {
        val scaleIn = ScaleAnimation(0.8f, 1f, 0.8f, 1f, Animation.RELATIVE_TO_SELF, 2.5f, Animation.RELATIVE_TO_SELF, 2.5f)
        scaleIn.duration = 600
        scaleIn.interpolator = DecelerateInterpolator()
        scaleIn.repeatCount = Animation.INFINITE
        scaleIn.repeatMode = Animation.REVERSE
        return scaleIn
    }

    private fun showToast(title: String, text: String) {
        val toast = Toast(applicationContext)
        toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        val grad = GradientDrawable()
        grad.setColor(-0x1)
        grad.cornerRadius = 6f
        grad.setStroke(2, -0x1000000)
        val tv = TextView(this)
        tv.text = Html.fromHtml("<b><font color=#f89406>$title:<br></font></b><br>$text")
        tv.textSize = 20f
        tv.gravity = Gravity.CENTER
        tv.background = grad
        toast.view = tv
        toast.show()
    }
}