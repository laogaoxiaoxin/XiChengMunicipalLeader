package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import kotlinx.android.synthetic.main.activity_choose_condition.*

class ChooseConditionActivity : AppCompatActivity() {

    private val listOfficeViews by lazy {
        arrayListOf<TextView>(tv_choose_office_line11, tv_choose_office_line12, tv_choose_office_line13, tv_choose_office_line14, tv_choose_office_line21, tv_choose_office_line22,
                tv_choose_office_line23, tv_choose_office_line24, tv_choose_office_line31, tv_choose_office_line32, tv_choose_office_line33, tv_choose_office_line34, tv_choose_office_line41, tv_choose_office_line42, tv_choose_office_line43, tv_choose_office_line44)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_condition)
        AutoUtils.auto(this)
        setClickListener()
        //
        when (intent.getStringExtra("type")) {
            "savecalc" -> {
                tv_choose_emergency_type.visibility = View.GONE
                ll_choose_emergency_type.visibility = View.GONE
                tv_choose_patrol_type.visibility = View.GONE
                ll_choose_patrol_type_line1.visibility = View.GONE
                ll_choose_patrol_type_line2.visibility = View.GONE
                ll_choose_cure_type.visibility = View.GONE
                tv_choose_cure_type.visibility = View.GONE
                ll_choose_rank_grade.visibility = View.GONE
                tv_choose_rank_grade.visibility = View.GONE
            }
            "cure" -> {
                cv_choose_office_line44.visibility = View.GONE
                tv_choose_patrol_type.visibility = View.GONE
                tv_choose_emergency_type.visibility = View.GONE
                ll_choose_emergency_type.visibility = View.GONE
                ll_choose_patrol_type_line1.visibility = View.GONE
                ll_choose_patrol_type_line2.visibility = View.GONE
            }
            "patrol" -> {
                cv_choose_office_line44.visibility = View.GONE
                ll_choose_emergency_type.visibility = View.GONE
                tv_choose_emergency_type.visibility = View.GONE
                ll_choose_cure_type.visibility = View.GONE
                tv_choose_cure_type.visibility = View.GONE
            }
            "emergency" -> {
                cv_choose_office_line44.visibility = View.GONE
                tv_choose_patrol_type.visibility = View.GONE
                ll_choose_patrol_type_line1.visibility = View.GONE
                ll_choose_patrol_type_line2.visibility = View.GONE
                ll_choose_cure_type.visibility = View.GONE
                tv_choose_cure_type.visibility = View.GONE
                //
                ll_choose_rank_grade.visibility = View.GONE
                tv_choose_rank_grade.visibility = View.GONE
            }
        }
    }

    private fun setClickListener() {
        listOfficeViews.forEach {
            it.setOnClickListener { tv ->
                setConditionText((tv as TextView).text.toString())
            }
        }
        choose_condition_back.setOnClickListener { finish() }
        //
        tv_choose_rank1.setOnClickListener { setConditionText(tv_choose_rank1.text.toString()) }
        tv_choose_rank2.setOnClickListener { setConditionText(tv_choose_rank2.text.toString()) }
        tv_choose_rank3.setOnClickListener { setConditionText(tv_choose_rank3.text.toString()) }
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
