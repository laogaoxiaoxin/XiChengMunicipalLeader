package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
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
        danger_rush_casedetail_copetime.text = info?.FirstDate
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
        //material
        displayItemsHasValue(info?.taskAsphalt_9cm_10, edt_emer_liqing9cm_010, ll_emer_liqing9cm_010)
        displayItemsHasValue(info?.taskAsphalt_5cm_10, edt_emer_liqing5cm_010, ll_emer_liqing5cm_010)
        displayItemsHasValue(info?.taskAsphalt_9cm_400, edt_emer_liqing9cm_over400, ll_emer_liqing9cm_over400)
        displayItemsHasValue(info?.taskAsphalt_5cm_400, edt_emer_liqing5cm_over400, ll_emer_liqing5cm_over400)
        displayItemsHasValue(info?.asphalt_9cm_10_400, edt_emer_liqing9cm_10400, ll_emer_liqing9cm_10400)
        displayItemsHasValue(info?.asphalt_5cm_10_400, edt_emer_liqing5cm_10400, ll_emer_liqing5cm_10400)
        displayItemsHasValue(info?.taskSidewalk, edt_emer_budao, ll_emer_budao)
        displayItemsHasValue(info?.taskInorganic_material_15cm, edt_emer_wujiliao15, ll_emer_wujiliao15)
        displayItemsHasValue(info?.taskInorganic_material_20cm, edt_emer_wujiliao20, ll_emer_wujiliao20)
        displayItemsHasValue(info?.taskInorganic_material_25cm, edt_emer_wujiliao25, ll_emer_wujiliao25)
        displayItemsHasValue(info?.luyuanshi, edt_emer_luyuanshi, ll_emer_luyuanshi)
        displayItemsHasValue(info?.jianchajing, edt_emer_jianchajing, ll_emer_jianchajing)
        displayItemsHasValue(info?.mohuimianji, edt_emer_mohuimianji, ll_emer_mohuimianji)
        displayItemsHasValue(info?.mohuihoudu, edt_emer_mohuihoudu, ll_emer_mohuihoudu)
        displayItemsHasValue(info?.guanchang, edt_emer_guanchang, ll_emer_guanchang)
        displayItemsHasValue(info?.guanjing, edt_emer_guanjing, ll_emer_guanjing)
        displayItemsHasValue(info?.guancai, edt_emer_guancai, ll_emer_guancai)
        //
        if (info?.prefirst == "null") danger_rush_casedetai_firsttimepic.visibility = View.GONE
        if (info?.underfirst == "null") danger_rush_casedetai_secondtimepic.visibility = View.GONE
        //
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.prefirst).into(danger_rush_casedetai_firsttimepic1)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.presecond).into(danger_rush_casedetai_firsttimepic2)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.prethird).into(danger_rush_casedetai_firsttimepic3)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.underfirst).into(danger_rush_casedetai_secondtimepic1)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.undersecond).into(danger_rush_casedetai_secondtimepic2)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.underthird).into(danger_rush_casedetai_secondtimepic3)
        //
        val dangerPhotos = arrayListOf(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.prefirst, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.presecond, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.prethird,
                XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.underfirst, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.undersecond, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + info?.underthird)
        val dangerViews = arrayListOf(danger_rush_casedetai_firsttimepic1, danger_rush_casedetai_firsttimepic2, danger_rush_casedetai_firsttimepic3, danger_rush_casedetai_secondtimepic1, danger_rush_casedetai_secondtimepic2, danger_rush_casedetai_secondtimepic3)
        dangerViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                val intent = Intent(this, LargePicActivity::class.java)
                intent.putExtra("picsurl", dangerPhotos)
                intent.putExtra("index", index)
                startActivity(intent)
            }
        }
    }

    private fun displayItemsHasValue(value: String?, textView: TextView, layout: LinearLayout) {
        if (value?.isNullOrEmpty() as Boolean || value == "null") {
            layout.visibility = View.GONE
        } else
            textView.text = value
    }
}
