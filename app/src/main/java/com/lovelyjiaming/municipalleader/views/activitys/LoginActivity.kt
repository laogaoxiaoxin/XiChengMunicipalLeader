package com.lovelyjiaming.municipalleader.views.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AutoUtils.setSize(this, false, 1080, 1920)
        setContentView(R.layout.activity_login)
        AutoUtils.auto(this)
    }
}
