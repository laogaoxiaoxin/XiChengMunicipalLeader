package com.lovelyjiaming.municipalleader.views.activitys

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
    }
}
