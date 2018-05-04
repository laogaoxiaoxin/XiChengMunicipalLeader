package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.EmergencyTaskItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.activity_danger_rush_case_detail.*

class DangerRushCaseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danger_rush_case_detail)
        AutoUtils.auto(this)
        danger_rush_casedetail_back.setOnClickListener { finish() }
        //
        val info = intent.getSerializableExtra("caseinfo") as EmergencyTaskItemClass?
        danger_rush_casedetail_type.text = info?.taskType
        danger_rush_casedetail_address.text = info?.taskPlace
        danger_rush_casedetail_assigngroup.text = info?.taskHome
        danger_rush_casedetail_office.text = info?.taskOffice
        danger_rush_casedetail_checkassigntime.text = info?.taskDate
        danger_rush_casedetail_copetime.text = info?.SecondDate
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.taskFirst).into(danger_rush_casedetai_firstpic)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.taskSecond).into(danger_rush_casedetai_secondpic)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.taskThird).into(danger_rush_casedetai_thirdpic)
        val listPics = arrayListOf(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.taskFirst, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.taskSecond,
                XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.taskThird)
        danger_rush_casedetai_firstpic.setOnClickListener {
            val intent = Intent(this, LargePicActivity::class.java)
            intent.putExtra("picsurl", listPics)
            intent.putExtra("index", 0)
            startActivity(intent)
        }
        danger_rush_casedetai_secondpic.setOnClickListener {
            val intent = Intent(this, LargePicActivity::class.java)
            intent.putExtra("picsurl", listPics)
            intent.putExtra("index", 1)
            startActivity(intent)
        }
        danger_rush_casedetai_thirdpic.setOnClickListener {
            val intent = Intent(this, LargePicActivity::class.java)
            intent.putExtra("picsurl", listPics)
            intent.putExtra("index", 2)
            startActivity(intent)
        }
    }
}
