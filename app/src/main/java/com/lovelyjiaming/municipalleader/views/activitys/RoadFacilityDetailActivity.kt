package com.lovelyjiaming.municipalleader.views.activitys

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS
import kotlinx.android.synthetic.main.activity_road_facility.*

data class FacilityDetailJson(val facilitiesDetails: FacilityDetail)
data class FacilityDetail(val taskName: String?, val startName: String?, val endName: String?, val postalEmpties: String?, val callBox: String?, val bin: String?, val busShelters: String?, val seat: String?, val mobileToilets: String?, val postalNewsstand: String?, val substationBox: String?, val meterBox: String?,
                          val tramSupplyBox: String?, val signalBrakeBox: String?, val streetLightBox: String?, val communicationBox: String?, val busStop: String?, val signpost: String?, val touristGuideSign: String?, val subwaySign: String?, val undergroundAccessSign: String?, val publicToiletSign: String?, val pedestrianGuideBoard: String?,
                          val bufferRod: String?, val treePool: String?, val busBay: String?, val temporaryParking: String?)

class RoadFacilityDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_road_facility)
        AutoUtils.auto(this)
        getData()
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        XCNetWorkUtil.invokeGetRequest(this, NETWORK_BASIC_SAVE_ADDRESS + "getFacilitiesDetails", {
            val result = Gson().fromJson(it, FacilityDetailJson::class.java)
            road_facility_detail_taskName.text = "道路名称：" + result.facilitiesDetails.taskName
            road_facility_detail_startName.text = "起点名称：" + result.facilitiesDetails.startName
            road_facility_detail_endName.text = "终点名称：" + result.facilitiesDetails.endName
            road_facility_detail_postalEmpties.text = "邮政信筒：" + result.facilitiesDetails.postalEmpties
            road_facility_detail_callBox.text = "公用电话亭：" + result.facilitiesDetails.callBox
            road_facility_detail_bin.text = "废物箱（垃圾箱、果皮）：" + result.facilitiesDetails.bin
            road_facility_detail_busShelters.text = "公交候车亭：" + result.facilitiesDetails.busShelters
            road_facility_detail_seat.text = "座椅：" + result.facilitiesDetails.seat
            road_facility_detail_mobileToilets.text = "移动厕所：" + result.facilitiesDetails.mobileToilets
            road_facility_detail_postalNewsstand.text = "邮政报刊亭：" + result.facilitiesDetails.postalNewsstand
            road_facility_detail_substationBox.text = "变电箱：" + result.facilitiesDetails.substationBox
            road_facility_detail_meterBox.text = "电表箱：" + result.facilitiesDetails.meterBox
            road_facility_detail_tramSupplyBox.text = "电车供电箱：" + result.facilitiesDetails.tramSupplyBox
            road_facility_detail_signalBrakeBox.text = "信号灯闸箱：" + result.facilitiesDetails.signalBrakeBox
            road_facility_detail_streetLightBox.text = "路灯闸箱：" + result.facilitiesDetails.streetLightBox
            road_facility_detail_communicationBox.text = "其他各类通信交换箱：" + result.facilitiesDetails.communicationBox
            road_facility_detail_busStop.text = "公交站牌：" + result.facilitiesDetails.busStop
            road_facility_detail_signpost.text = "指路牌：" + result.facilitiesDetails.signpost
            road_facility_detail_touristGuideSign.text = "景区引导牌：" + result.facilitiesDetails.touristGuideSign
            road_facility_detail_subwaySign.text = "地铁指示牌：" + result.facilitiesDetails.subwaySign
            road_facility_detail_undergroundAccessSign.text = "地下通道指示牌：" + result.facilitiesDetails.undergroundAccessSign
            road_facility_detail_publicToiletSign.text = "公共厕所指示牌：" + result.facilitiesDetails.publicToiletSign
            road_facility_detail_pedestrianGuideBoard.text = "人行导向牌：" + result.facilitiesDetails.pedestrianGuideBoard
            road_facility_detail_bufferRod.text = "挡车杆：" + result.facilitiesDetails.bufferRod
            road_facility_detail_treePool.text = "树池：" + result.facilitiesDetails.treePool
            road_facility_detail_busBay.text = "公交港湾：" + result.facilitiesDetails.busBay
            road_facility_detail_temporaryParking.text = "临时停车场：" + result.facilitiesDetails.temporaryParking
        }, hashMapOf("id" to intent.getStringExtra("id")))
    }
}
