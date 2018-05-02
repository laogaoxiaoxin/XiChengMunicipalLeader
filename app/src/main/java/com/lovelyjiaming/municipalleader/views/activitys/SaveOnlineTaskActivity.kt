package com.lovelyjiaming.municipalleader.views.activitys

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
    }
}
