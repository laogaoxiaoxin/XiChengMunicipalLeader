package com.lovelyjiaming.municipalleader.views.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import kotlinx.android.synthetic.main.activity_choose_condition.*

class ChooseConditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_condition)
        AutoUtils.auto(this)
        this.setClickListener()
    }

    private fun setClickListener() {
        choose_condition_back.setOnClickListener { finish() }
    }
}
