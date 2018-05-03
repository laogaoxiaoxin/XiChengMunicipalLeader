package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.EngineerItem
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.activity_engineer_detail.*

class EngineerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_engineer_detail)
        AutoUtils.auto(this)
        engineer_usual_detail_back.setOnClickListener { finish() }
        //
        val info = intent.getSerializableExtra("enginfo") as EngineerItem
        engineer_usual_detail_name.text = info.name
        engineer_usual_detail_project_intro.text = info.introduction
        engineer_usual_detail_starttime.text = info.start
        engineer_usual_detail_endtime.text = info.done
        engineer_usual_detail_workprogress.text = info.pace
        engineer_usual_detail_responser.text = info.person
        engineer_usual_detail_daily_report.text = info.remarks
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info.picfirst).into(engineer_usual_detail_first)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info.picsecond).into(engineer_usual_detail_second)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info.picthird).into(engineer_usual_detail_third)

        engineer_usual_detail_first.setOnClickListener {
            val intent = Intent(this, LargePicActivity::class.java)
            intent.putExtra("picurl", XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info.picfirst)
            startActivity(intent)
        }
        engineer_usual_detail_second.setOnClickListener {
            val intent = Intent(this, LargePicActivity::class.java)
            intent.putExtra("picurl", XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info.picsecond)
            startActivity(intent)
        }
        engineer_usual_detail_third.setOnClickListener {
            val intent = Intent(this, LargePicActivity::class.java)
            intent.putExtra("picurl", XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info.picthird)
            startActivity(intent)
        }

    }
}
