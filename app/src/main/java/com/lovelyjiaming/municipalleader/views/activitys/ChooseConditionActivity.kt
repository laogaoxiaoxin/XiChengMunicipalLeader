package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import kotlinx.android.synthetic.main.activity_choose_condition.*

class ChooseConditionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_condition)
        AutoUtils.auto(this)
        setClickListener()
    }

    private fun setClickListener() {
        choose_condition_back.setOnClickListener { finish() }
        //
        tv_choose_way_name.setOnClickListener { setConditionText(tv_choose_way_name.text.toString()) }
        tv_choose_way_num.setOnClickListener { setConditionText(tv_choose_way_num.text.toString()) }
        tv_choose_way_office.setOnClickListener { setConditionText(tv_choose_way_office.text.toString()) }
        tv_choose_way_date.setOnClickListener { setConditionText(tv_choose_way_date.text.toString()) }
        //
        tv_choose_patrol_type_spjl.setOnClickListener { setConditionText(tv_choose_patrol_type_spjl.text.toString()) }
        tv_choose_patrol_type_sjsz.setOnClickListener { setConditionText(tv_choose_patrol_type_sjsz.text.toString()) }
        tv_choose_patrol_type_tccxc.setOnClickListener { setConditionText(tv_choose_patrol_type_tccxc.text.toString()) }
        tv_choose_patrol_type_gzzxc.setOnClickListener { setConditionText(tv_choose_patrol_type_gzzxc.text.toString()) }
        tv_choose_patrol_type_lctc.setOnClickListener { setConditionText(tv_choose_patrol_type_lctc.text.toString()) }
        tv_choose_patrol_type_ggfwss.setOnClickListener { setConditionText(tv_choose_patrol_type_ggfwss.text.toString()) }
        //
        tv_choose_cure_type_dlps.setOnClickListener { setConditionText(tv_choose_cure_type_dlps.text.toString()) }
        tv_choose_cure_type_bdps.setOnClickListener { setConditionText(tv_choose_cure_type_bdps.text.toString()) }
        tv_choose_cure_type_fsssps.setOnClickListener { setConditionText(tv_choose_cure_type_fsssps.text.toString()) }
        //
        tv_choose_emergency_type_dltx.setOnClickListener { setConditionText(tv_choose_emergency_type_dltx.text.toString()) }
        tv_choose_emergency_type_gdtx.setOnClickListener { setConditionText(tv_choose_emergency_type_gdtx.text.toString()) }
    }

    private fun setConditionText(sText: String) {
        val intent = Intent()
        intent.putExtra("condition", sText)
        setResult(1046, intent)
        finish()
    }
}
