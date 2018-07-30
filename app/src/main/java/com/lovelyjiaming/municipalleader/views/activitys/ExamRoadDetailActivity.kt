package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.activity_exam_road_detail.*
import java.io.Serializable

//审批掘路详情信息
data class InspectExamRoad(val name: String, val number: String, val nature: String, val team: String, val place: String, val start: String, val finish: String, val fileone: String, val filetwo: String, val filethree: String, val filefour: String, val filefive: String, val filesix: String)

data class InspectExamRoadDetail(val InspectExamRoad: InspectExamRoad)

//监管日志历史图片
data class ExamRoadDetailHelpItem(val date: String, val first: String, val second: String, val third: String, val fourth: String, val examRemark: String) : Serializable

data class ExamRoadDetailHelp(val help: MutableList<ExamRoadDetailHelpItem>)

var g_mapOfMoreDailyInfo: MutableMap<String, List<ExamRoadDetailHelpItem>> = mutableMapOf()

class ExamRoadDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_road_detail)
        AutoUtils.auto(this)
        //
        check_exam_road_detail_back.setOnClickListener { finish() }
        val number = intent.getStringExtra("number")

        XCNetWorkUtil.invokeGetRequest(this, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getExamingRoadDateils", {
            val result = Gson().fromJson(it, InspectExamRoadDetail::class.java)
            check_exam_road_detail_name.text = result.InspectExamRoad.name
            check_exam_road_detail_number.text = result.InspectExamRoad.number
            check_exam_road_detail_type.text = result.InspectExamRoad.nature
            check_exam_road_detail_patrolgroup.text = result.InspectExamRoad.team
            check_exam_road_detail_address.text = result.InspectExamRoad.place
            check_exam_road_detail_start.text = result.InspectExamRoad.start
            check_exam_road_detail_end.text = result.InspectExamRoad.finish
            //
            check_exam_road_detail_more_photo.setOnClickListener {
                startActivity(Intent(this, MoreExamRoadHistoryDailyActivity::class.java))
            }
            //
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.fileone).into(check_exam_road_detail_firstpic)
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filetwo).into(check_exam_road_detail_secondpic)
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filethree).into(check_exam_road_detail_thirdpic)
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filefour).into(check_exam_road_detail_fourthpic)
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filefive).into(check_exam_road_detail_fifthpic)
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filesix).into(check_exam_road_detail_sixthpic)
            //
            val ivViews = mutableListOf<ImageView>(check_exam_road_detail_firstpic, check_exam_road_detail_secondpic, check_exam_road_detail_thirdpic, check_exam_road_detail_fourthpic, check_exam_road_detail_fifthpic, check_exam_road_detail_sixthpic)
            ivViews.forEachIndexed { index, imageview ->
                imageview.setOnClickListener {
                    val intent = Intent(this, LargePicActivity::class.java)
                    intent.putExtra("picsurl", arrayListOf(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.fileone, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filetwo, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filethree,
                            XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filefour, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filefive, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + result.InspectExamRoad.filesix))
                    intent.putExtra("index", index)
                    startActivity(intent)
                }
            }

            //更多监管日志
            XCNetWorkUtil.invokeGetRequest(this, "http://39.104.80.111:8888/FloodServer_First/Examing_Road_Underway_Journal_Listview?" + "flagname=${result.InspectExamRoad.name}" + "&flagnumber=${result.InspectExamRoad.number}", {
                getHistoryPhotoInfo(it)
            })
            //
        }, hashMapOf("number" to number))
    }

    private fun getHistoryPhotoInfo(strResult: String) {
        val result = Gson().fromJson(strResult, ExamRoadDetailHelp::class.java)
        g_mapOfMoreDailyInfo = result.help.groupBy {
            val str = it.date.replace("-", "").substring(0, 6)
            str
        }.toMutableMap()
    }
}
