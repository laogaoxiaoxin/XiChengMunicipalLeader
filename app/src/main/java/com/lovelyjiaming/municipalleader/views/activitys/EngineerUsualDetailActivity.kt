package com.lovelyjiaming.municipalleader.views.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import kotlinx.android.synthetic.main.activity_engineer_usual_detail.*

class EngineerUsualDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_engineer_usual_detail)
        AutoUtils.auto(this)

        engineer_usual_detail_back.setOnClickListener { finish() }
    }
}
