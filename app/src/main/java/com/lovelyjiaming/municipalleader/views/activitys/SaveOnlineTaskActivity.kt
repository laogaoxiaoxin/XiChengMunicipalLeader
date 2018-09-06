package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.CureDetailInfo
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS
import kotlinx.android.synthetic.main.activity_save_online_task.*

class SaveOnlineTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_online_task)
        AutoUtils.auto(this)
        save_online_taskdetail_back.setOnClickListener { finish() }
        //
        val taskNumber = intent.getStringExtra("taskNumber")
        //请求详情
        XCNetWorkUtil.invokeGetRequest(this, NETWORK_BASIC_SAVE_ADDRESS + "getOnLineDetails", {
            val taskInfo = Gson().fromJson(it, CureDetailInfo::class.java).CureOnLineDetails
            //
            displayItemsHasValue(taskInfo.taskAsphalt_9cm_10, save_online_taskdetail_liqing9_010, ll_save_online_taskdetail_liqing9_010)
            displayItemsHasValue(taskInfo.taskAsphalt_5cm_10, save_online_taskdetail_liqing5_010, ll_save_online_taskdetail_liqing5_010)
            displayItemsHasValue(taskInfo.asphalt_9cm_10_400, save_online_taskdetail_liqing9_10400, ll_save_online_taskdetail_liqing9_10400)
            displayItemsHasValue(taskInfo.asphalt_5cm_10_400, save_online_taskdetail_liqing5_10400, ll_save_online_taskdetail_liqing5_10400)
            displayItemsHasValue(taskInfo.taskAsphalt_9cm_400, save_online_taskdetail_liqing9_above400, ll_save_online_taskdetail_liqing9_above400)
            displayItemsHasValue(taskInfo.taskAsphalt_5cm_400, save_online_taskdetail_liqing5_above400, ll_save_online_taskdetail_liqing5_above400)
            displayItemsHasValue(taskInfo.taskSidewalk, save_online_taskdetail_sidewalk, ll_save_online_taskdetail_sidewalk)
            displayItemsHasValue(taskInfo.taskPlaster, save_online_taskdetail_plaster, ll_save_online_taskdetail_plaster)
            displayItemsHasValue(taskInfo.caiselumian, save_online_taskdetail_caiselumain, ll_save_online_taskdetail_caiselumain)
            displayItemsHasValue(taskInfo.taskRainwater_outlet, save_online_taskdetail_yushuikou, ll_save_online_taskdetail_yushuikou)
            displayItemsHasValue(taskInfo.tiezhidangchezhuang, save_online_taskdetail_tiezhidangchezhuang, ll_save_online_taskdetail_tiezhidangchezhuang)
            displayItemsHasValue(taskInfo.shicaidangchezhuang, save_online_taskdetail_shicaidangchezhuang, ll_save_online_taskdetail_shicaidangchezhuang)
            displayItemsHasValue(taskInfo.shicailuyuanshi, save_online_taskdetail_shicailuyuanshi, ll_save_online_taskdetail_shicailuyuanshi)
            displayItemsHasValue(taskInfo.shicaimangdao, save_online_taskdetail_shicaimangdao, ll_save_online_taskdetail_shicaimangdao)
            displayItemsHasValue(taskInfo.shicaibudao, save_online_taskdetail_shicaibudao, ll_save_online_taskdetail_shicaibudao)
            displayItemsHasValue(taskInfo.mangdao, save_online_taskdetail_mangdao, ll_save_online_taskdetail_mangdao)
            displayItemsHasValue(taskInfo.taskTree_pool, save_online_taskdetail_taskTree_pool, ll_save_online_taskdetail_taskTree_pool)
            displayItemsHasValue(taskInfo.wujiliao25, save_online_taskdetail_wujiliao25, ll_save_online_taskdetail_wujiliao25)
            displayItemsHasValue(taskInfo.taskInorganic_material_20cm, save_online_taskdetail_taskInorganic_material_20cm, ll_save_online_taskdetail_taskInorganic_material_20cm)
            displayItemsHasValue(taskInfo.taskInorganic_material_15cm, save_online_taskdetail_taskInorganic_material_15cm, ll_save_online_taskdetail_taskInorganic_material_15cm)
            displayItemsHasValue(taskInfo.taskMachine_stone, save_online_taskdetail_taskMachine_stone, ll_save_online_taskdetail_taskMachine_stone)
            displayItemsHasValue(taskInfo.taskCurb, save_online_taskdetail_curb, ll_save_online_taskdetail_curb)
            displayItemsHasValue(taskInfo.jiagujianchajing, save_online_taskdetail_jiagujianchajing, ll_save_online_taskdetail_jiagujianchajing)
            displayItemsHasValue(taskInfo.shengjiangjianchajing, save_online_taskdetail_shengjiangjianchajing, ll_save_online_taskdetail_shengjiangjianchajing)
            save_online_taskdetail_num.text = taskInfo.taskNumber
            save_online_taskdetail_name.text = taskInfo.taskName
            save_online_taskdetail_addr.text = taskInfo.taskPlace
            save_online_taskdetail_level.text = taskInfo.taskRank
            save_online_taskdetail_office.text = taskInfo.taskOffice
            save_online_taskdetail_type.text = taskInfo.taskType
            save_online_taskdetail_time.text = taskInfo.taskDate
            save_online_taskdetail_assigngroup.text = taskInfo.taskAssign
            save_online_taskdetail_assignstate.text = taskInfo.taskState
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskFirst).into(save_online_taskdetail_firstpic)
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskSecond).into(save_online_taskdetail_secondpic)
            Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskThird).into(save_online_taskdetail_thirdpic)
            val listPics = arrayListOf(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskFirst, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskSecond,
                    XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskThird)
            save_online_taskdetail_firstpic.setOnClickListener {
                val intent = Intent(this, LargePicActivity::class.java)
                intent.putExtra("picsurl", listPics)
                intent.putExtra("index", 0)
                startActivity(intent)
            }
            save_online_taskdetail_secondpic.setOnClickListener {
                val intent = Intent(this, LargePicActivity::class.java)
                intent.putExtra("picsurl", listPics)
                intent.putExtra("index", 1)
                startActivity(intent)
            }
            save_online_taskdetail_thirdpic.setOnClickListener {
                val intent = Intent(this, LargePicActivity::class.java)
                intent.putExtra("picsurl", listPics)
                intent.putExtra("index", 2)
                startActivity(intent)
            }
        }, hashMapOf("taskNumber" to taskNumber))
    }

    private fun displayItemsHasValue(value: String?, textView: TextView, layout: LinearLayout) {
        if (value?.isNullOrEmpty() as Boolean) {
            layout.visibility = View.GONE
        } else
            textView.text = value
    }
}
