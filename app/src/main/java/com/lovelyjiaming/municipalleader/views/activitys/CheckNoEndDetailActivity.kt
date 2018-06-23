package com.lovelyjiaming.municipalleader.views.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import kotlinx.android.synthetic.main.activity_check_no_end_detail.*

class CheckNoEndDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_no_end_detail)
        AutoUtils.auto(this)
        //
        check_noend_taskdetail_back.setOnClickListener { finish() }
        //
        val taskInfo: InspectUndoneItemClass = intent.getSerializableExtra("taskinfo") as InspectUndoneItemClass
        //
        check_noend_taskdetail_name.text = taskInfo.taskName
        check_noend_taskdetail_num.text = taskInfo.taskNumber
        check_noend_taskdetail_type.text = taskInfo.taskType
        check_noend_taskdetail_rank.text = taskInfo.taskRank
        check_noend_taskdetail_office.text = taskInfo.taskOffice
        check_noend_taskdetail_patrol_date.text = taskInfo.taskDate
        check_noend_taskdetail_cure_date.text = taskInfo.taskAssignDate
        check_noend_taskdetail_cure_group.text = taskInfo.taskAssign
        check_noend_taskdetail_status.text = taskInfo.taskState
        check_noend_taskdetail_address.text = taskInfo.taskPlace
        //
        displayItemsHasValue(taskInfo.taskAsphalt_9cm_10, check_noend_taskdetail_liqing9_010, ll_check_noend_taskdetail_liqing9_010)
        displayItemsHasValue(taskInfo.taskAsphalt_5cm_10, check_noend_taskdetail_liqing5_010, ll_check_noend_taskdetail_liqing5_010)
        displayItemsHasValue(taskInfo.asphalt_9cm_10_400, check_noend_taskdetail_liqing9_10400, ll_check_noend_taskdetail_liqing9_10400)
        displayItemsHasValue(taskInfo.asphalt_5cm_10_400, check_noend_taskdetail_liqing5_10400, ll_check_noend_taskdetail_liqing5_10400)
        displayItemsHasValue(taskInfo.taskAsphalt_9cm_400, check_noend_taskdetail_liqing9_above400, ll_check_noend_taskdetail_liqing9_above400)
        displayItemsHasValue(taskInfo.taskAsphalt_5cm_400, check_noend_taskdetail_liqing5_above400, ll_check_noend_taskdetail_liqing5_above400)
        displayItemsHasValue(taskInfo.taskSidewalk, check_noend_taskdetail_sidewalk, ll_check_noend_taskdetail_sidewalk)
        displayItemsHasValue(taskInfo.taskPlaster, check_noend_taskdetail_plaster, ll_check_noend_taskdetail_plaster)
        displayItemsHasValue(taskInfo.caiselumian, check_noend_taskdetail_caiselumain, ll_check_noend_taskdetail_caiselumain)
        displayItemsHasValue(taskInfo.taskRainwater_outlet, check_noend_taskdetail_yushuikou, ll_check_noend_taskdetail_yushuikou)
        displayItemsHasValue(taskInfo.tiezhidangchezhuang, check_noend_taskdetail_tiezhidangchezhuang, ll_check_noend_taskdetail_tiezhidangchezhuang)
        displayItemsHasValue(taskInfo.shicaidangchezhuang, check_noend_taskdetail_shicaidangchezhuang, ll_check_noend_taskdetail_shicaidangchezhuang)
        displayItemsHasValue(taskInfo.shicailuyuanshi, check_noend_taskdetail_shicailuyuanshi, ll_check_noend_taskdetail_shicailuyuanshi)
        displayItemsHasValue(taskInfo.shicaimangdao, check_noend_taskdetail_shicaimangdao, ll_check_noend_taskdetail_shicaimangdao)
        displayItemsHasValue(taskInfo.shicaibudao, check_noend_taskdetail_shicaibudao, ll_check_noend_taskdetail_shicaibudao)
        displayItemsHasValue(taskInfo.mangdao, check_noend_taskdetail_mangdao, ll_check_noend_taskdetail_mangdao)
        displayItemsHasValue(taskInfo.taskTree_pool, check_noend_taskdetail_taskTree_pool, ll_check_noend_taskdetail_taskTree_pool)
        displayItemsHasValue(taskInfo.wujiliao25, check_noend_taskdetail_wujiliao25, ll_check_noend_taskdetail_wujiliao25)
        displayItemsHasValue(taskInfo.taskInorganic_material_20cm, check_noend_taskdetail_taskInorganic_material_20cm, ll_check_noend_taskdetail_taskInorganic_material_20cm)
        displayItemsHasValue(taskInfo.taskInorganic_material_15cm, check_noend_taskdetail_taskInorganic_material_15cm, ll_check_noend_taskdetail_taskInorganic_material_15cm)
        displayItemsHasValue(taskInfo.taskMachine_stone, check_noend_taskdetail_taskMachine_stone, ll_check_noend_taskdetail_taskMachine_stone)
        displayItemsHasValue(taskInfo.taskCurb, check_noend_taskdetail_curb, ll_check_noend_taskdetail_curb)
        displayItemsHasValue(taskInfo.jiagujianchajing, check_noend_taskdetail_jiagujianchajing, ll_check_noend_taskdetail_jiagujianchajing)
        displayItemsHasValue(taskInfo.shengjiangjianchajing, check_noend_taskdetail_shengjiangjianchajing, ll_check_noend_taskdetail_shengjiangjianchajing)

    }

    private fun displayItemsHasValue(value: String?, textView: TextView, layout: LinearLayout) {
        if (value?.isNullOrEmpty() as Boolean) {
            layout.visibility = View.GONE
        } else
            textView.text = value
    }
}
