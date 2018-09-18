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
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
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
        check_noend_taskdetail_cure_date.text = taskInfo.taskNote
        check_noend_taskdetail_cure_group.text = taskInfo.taskRemarks
        check_noend_taskdetail_cure_group.text = taskInfo.taskAssign
        check_noend_taskdetail_status.text = taskInfo.taskState
        check_noend_taskdetail_address.text = taskInfo.taskPlace
        //
        displayItemsHasValue("", check_noend_taskdetail_liqing9_010, ll_check_noend_taskdetail_liqing9_010)
        displayItemsHasValue("", check_noend_taskdetail_liqing5_010, ll_check_noend_taskdetail_liqing5_010)
        displayItemsHasValue("", check_noend_taskdetail_liqing9_10400, ll_check_noend_taskdetail_liqing9_10400)
        displayItemsHasValue("", check_noend_taskdetail_liqing5_10400, ll_check_noend_taskdetail_liqing5_10400)
        displayItemsHasValue("", check_noend_taskdetail_liqing9_above400, ll_check_noend_taskdetail_liqing9_above400)
        displayItemsHasValue("", check_noend_taskdetail_liqing5_above400, ll_check_noend_taskdetail_liqing5_above400)
        displayItemsHasValue("", check_noend_taskdetail_sidewalk, ll_check_noend_taskdetail_sidewalk)
        displayItemsHasValue("", check_noend_taskdetail_plaster, ll_check_noend_taskdetail_plaster)
        displayItemsHasValue("", check_noend_taskdetail_caiselumain, ll_check_noend_taskdetail_caiselumain)
        displayItemsHasValue("", check_noend_taskdetail_yushuikou, ll_check_noend_taskdetail_yushuikou)
        displayItemsHasValue("", check_noend_taskdetail_tiezhidangchezhuang, ll_check_noend_taskdetail_tiezhidangchezhuang)
        displayItemsHasValue("", check_noend_taskdetail_shicaidangchezhuang, ll_check_noend_taskdetail_shicaidangchezhuang)
        displayItemsHasValue("", check_noend_taskdetail_shicailuyuanshi, ll_check_noend_taskdetail_shicailuyuanshi)
        displayItemsHasValue("", check_noend_taskdetail_shicaimangdao, ll_check_noend_taskdetail_shicaimangdao)
        displayItemsHasValue("", check_noend_taskdetail_shicaibudao, ll_check_noend_taskdetail_shicaibudao)
        displayItemsHasValue("", check_noend_taskdetail_mangdao, ll_check_noend_taskdetail_mangdao)
        displayItemsHasValue("", check_noend_taskdetail_taskTree_pool, ll_check_noend_taskdetail_taskTree_pool)
        displayItemsHasValue("", check_noend_taskdetail_wujiliao25, ll_check_noend_taskdetail_wujiliao25)
        displayItemsHasValue("", check_noend_taskdetail_taskInorganic_material_20cm, ll_check_noend_taskdetail_taskInorganic_material_20cm)
        displayItemsHasValue("", check_noend_taskdetail_taskInorganic_material_15cm, ll_check_noend_taskdetail_taskInorganic_material_15cm)
        displayItemsHasValue("", check_noend_taskdetail_taskMachine_stone, ll_check_noend_taskdetail_taskMachine_stone)
        displayItemsHasValue("", check_noend_taskdetail_curb, ll_check_noend_taskdetail_curb)
        displayItemsHasValue("", check_noend_taskdetail_jiagujianchajing, ll_check_noend_taskdetail_jiagujianchajing)
        displayItemsHasValue("", check_noend_taskdetail_shengjiangjianchajing, ll_check_noend_taskdetail_shengjiangjianchajing)
        //
        val patrolPhotos = arrayListOf(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskFirst, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskSecond, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskThird)
        val patrolViews = arrayListOf(check_noend_casedetai_firstpic, check_noend_casedetai_secondpic, check_noend_casedetai_thirdpic)
        patrolViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                val intent = Intent(this, LargePicActivity::class.java)
                intent.putExtra("picsurl", patrolPhotos)
                intent.putExtra("index", index)
                startActivity(intent)
            }
        }
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskFirst).into(check_noend_casedetai_firstpic)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskSecond).into(check_noend_casedetai_secondpic)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.taskThird).into(check_noend_casedetai_thirdpic)

        //
        val curePhotos = arrayListOf(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.finishFirst, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.finishSecond, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.finishThird)
        val cureViews = arrayListOf(check_noend_casedetai_prefirst, check_noend_casedetai_presecond, check_noend_casedetai_prethird)
        cureViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                val intent = Intent(this, LargePicActivity::class.java)
                intent.putExtra("picsurl", curePhotos)
                intent.putExtra("index", index)
                startActivity(intent)
            }
        }
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.finishFirst).into(check_noend_casedetai_prefirst)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.finishSecond).into(check_noend_casedetai_presecond)
        Glide.with(this).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + taskInfo.finishThird).into(check_noend_casedetai_prethird)
    }

    private fun displayItemsHasValue(value: String?, textView: TextView, layout: LinearLayout) {
        if (value?.isNullOrEmpty() as Boolean) {
            layout.visibility = View.GONE
        } else
            textView.text = value
    }
}
