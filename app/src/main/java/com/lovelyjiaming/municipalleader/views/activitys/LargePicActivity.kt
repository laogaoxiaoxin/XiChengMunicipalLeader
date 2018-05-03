package com.lovelyjiaming.municipalleader.views.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.activity_large_pic.*

class LargePicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_large_pic)

        val url = intent.getStringExtra("picurl")
        Glide.with(this).load(url).into(all_largepic)
        all_largepic.setOnClickListener { finish() }
        largepiclayout.setOnClickListener { finish() }
    }

}
