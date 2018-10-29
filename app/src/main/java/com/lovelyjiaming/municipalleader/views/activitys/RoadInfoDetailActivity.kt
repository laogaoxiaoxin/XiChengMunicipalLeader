package com.lovelyjiaming.municipalleader.views.activitys

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.activity_road_info_detail.*

data class RoadInfoJson(val RoadInfoDetails: RoadInfoDetails)
data class RoadInfoDetails(val id: String?, val taskName: String?, val taskStreet: String?, val startName: String?, val endName: String?, val roadLength: String?, val roadWidth: String?, val roadArea: String?, val trailsLength: String?, val trailsWidth: String?, val trailsArea: String?, val stoneLength: String?, val stoneWidth: String?,
                           val stoneArea: String?, val motorwayNumber: String?, val motorwayLength: String?, val motorwayWidth: String?, val motorwayArea: String?, val nonMotorwayLength: String?, val nonMotorwayWidth: String?, val nonMotorwayArea: String?, val coloredAsphaltArea: String?, val roadRank: String?,
                           val pavementMaterials: String?, val accessibleRamp: String?, val overpasses: String?, val subwayExits: String?, val undergroundPassage: String?)

class RoadInfoDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road_info_detail)
        AutoUtils.auto(this)
//
        XCNetWorkUtil.invokeGetRequest(this, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getRoadInfoDetails", {
            val result = Gson().fromJson(it, RoadInfoJson::class.java)
            road_info_detail_taskName.text = "道路名称：" + result.RoadInfoDetails.taskName
            road_info_detail_taskStreet.text = "所属街道：" + result.RoadInfoDetails.taskStreet
            road_info_detail_startName.text = "起点名称：" + result.RoadInfoDetails.startName
            road_info_detail_endName.text = "终点名称：" + result.RoadInfoDetails.endName
            road_info_detail_roadLength.text = "路面长度：" + result.RoadInfoDetails.roadLength
            road_info_detail_roadWidth.text = "路面宽度：" + result.RoadInfoDetails.roadWidth
            road_info_detail_roadArea.text = "路面面积：" + result.RoadInfoDetails.roadArea
            road_info_detail_trailsLength.text = "步道长度：" + result.RoadInfoDetails.trailsLength
            road_info_detail_trailsWidth.text = "步道宽度：" + result.RoadInfoDetails.trailsWidth
            road_info_detail_trailsArea.text = "步道面积：" + result.RoadInfoDetails.trailsArea
            road_info_detail_stoneLength.text = "石材长度：" + result.RoadInfoDetails.stoneLength
            road_info_detail_stoneWidth.text = "石材宽度：" + result.RoadInfoDetails.stoneWidth
            road_info_detail_stoneArea.text = "石材面积：" + result.RoadInfoDetails.stoneArea
            road_info_detail_motorwayNumber.text = "机动车道数：" + result.RoadInfoDetails.motorwayNumber
            road_info_detail_motorwayLength.text = "机动车道长度：" + result.RoadInfoDetails.motorwayLength
            road_info_detail_motorwayWidth.text = "机动车道宽度：" + result.RoadInfoDetails.motorwayWidth
            road_info_detail_motorwayArea.text = "机动车道面积：" + result.RoadInfoDetails.motorwayArea
            road_info_detail_nonMotorwayLength.text = "非机动车道长度：" + result.RoadInfoDetails.nonMotorwayLength
            road_info_detail_nonMotorwayWidth.text = "非机动车道长度：" + result.RoadInfoDetails.nonMotorwayWidth
            road_info_detail_nonMotorwayArea.text = "非机动车道长度：" + result.RoadInfoDetails.nonMotorwayArea
            road_info_detail_coloredAsphaltArea.text = "彩色沥青面积：" + result.RoadInfoDetails.coloredAsphaltArea
            road_info_detail_roadRank.text = "道路等级：" + result.RoadInfoDetails.roadRank
            road_info_detail_pavementMaterials.text = "铺装材料：" + result.RoadInfoDetails.pavementMaterials
            road_info_detail_accessibleRamp.text = "无障碍坡道：" + result.RoadInfoDetails.accessibleRamp
            road_info_detail_overpasses.text = "过街天桥：" + result.RoadInfoDetails.overpasses
            road_info_detail_subwayExits.text = "地铁出入口：" + result.RoadInfoDetails.subwayExits
            road_info_detail_undergroundPassage.text = "地下通道：" + result.RoadInfoDetails.undergroundPassage

        }, hashMapOf("id" to intent.getStringExtra("id")))
    }


}
