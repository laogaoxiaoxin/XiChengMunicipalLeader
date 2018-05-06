package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.activity_save_online_task.*

class SaveOnlineTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_online_task)
        AutoUtils.auto(this)
        save_online_taskdetail_back.setOnClickListener { finish() }
        //
        val taskInfo = intent.getSerializableExtra("taskinfo") as InspectUndoneItemClass
        save_online_taskdetail_num.text = taskInfo.taskNumber
        save_online_taskdetail_name.text = taskInfo.taskName
        save_online_taskdetail_addr.text = taskInfo.taskPlace
        save_online_taskdetail_level.text = taskInfo.taskRank
        save_online_taskdetail_office.text = taskInfo.taskOffice
        save_online_taskdetail_type.text = taskInfo.taskType
        save_online_taskdetail_time.text = taskInfo.taskAssignDate
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


        //
        save_online_taskdetail_liqing9_010.text = taskInfo.taskAsphalt_9cm_10
        save_online_taskdetail_liqing5_010.text = taskInfo.taskAsphalt_5cm_10
        save_online_taskdetail_liqing9_10400.text = taskInfo.asphalt_9cm_10_400
        save_online_taskdetail_liqing5_10400.text = taskInfo.asphalt_5cm_10_400
        save_online_taskdetail_liqing9_above400.text = taskInfo.taskAsphalt_9cm_400
        save_online_taskdetail_liqing5_above400.text = taskInfo.taskAsphalt_5cm_400
        save_online_taskdetail_sidewalk.text = taskInfo.taskSidewalk
        save_online_taskdetail_plaster.text = taskInfo.taskPlaster
        save_online_taskdetail_caiselumain.text = taskInfo.caiselumian
        save_online_taskdetail_yushuikou.text = taskInfo.taskRainwater_outlet
        //
        save_online_taskdetail_tiezhidangchezhuang.text = taskInfo.tiezhidangchezhuang
        save_online_taskdetail_shicaidangchezhuang.text = taskInfo.shicaidangchezhuang
        save_online_taskdetail_shicailuyuanshi.text = taskInfo.shicailuyuanshi
        save_online_taskdetail_shicaimangdao.text = taskInfo.shicaimangdao
        save_online_taskdetail_shicaibudao.text = taskInfo.shicaibudao
        save_online_taskdetail_mangdao.text = taskInfo.mangdao
        save_online_taskdetail_taskTree_pool.text = taskInfo.taskTree_pool
        save_online_taskdetail_wujiliao25.text = taskInfo.wujiliao25
        save_online_taskdetail_taskInorganic_material_20cm.text = taskInfo.taskInorganic_material_20cm
        save_online_taskdetail_taskInorganic_material_15cm.text = taskInfo.taskInorganic_material_15cm
        save_online_taskdetail_taskMachine_stone.text = taskInfo.taskMachine_stone
        save_online_taskdetail_curb.text = taskInfo.taskCurb
    }
}
