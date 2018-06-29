package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import kotlinx.android.synthetic.main.activity_choose_condition.*

class ChooseConditionActivity : AppCompatActivity() {
    //回传给页面的值
    private val hashMapValue: HashMap<String, String> = hashMapOf()

    private val listOfficeViews by lazy {
        arrayListOf<TextView>(tv_choose_office_line11, tv_choose_office_line12, tv_choose_office_line13, tv_choose_office_line14, tv_choose_office_line21, tv_choose_office_line22,
                tv_choose_office_line23, tv_choose_office_line24, tv_choose_office_line31, tv_choose_office_line32, tv_choose_office_line33, tv_choose_office_line34, tv_choose_office_line41, tv_choose_office_line42, tv_choose_office_line43, tv_choose_office_line44)
    }
    private val listOfficeCvViews by lazy {
        arrayListOf<CardView>(cv_choose_office_line11, cv_choose_office_line12, cv_choose_office_line13, cv_choose_office_line14, cv_choose_office_line21, cv_choose_office_line22, cv_choose_office_line23,
                cv_choose_office_line24, cv_choose_office_line31, cv_choose_office_line32, cv_choose_office_line33, cv_choose_office_line34, cv_choose_office_line41, cv_choose_office_line42, cv_choose_office_line43, cv_choose_office_line44)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_condition)
        AutoUtils.auto(this)
        setClickListener()
        //
        when (intent.getStringExtra("type")) {
        //养护的综合统计
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
                ll_choose_date.visibility = View.GONE
            }
            "cure" -> {
                cv_choose_office_line44.visibility = View.GONE
                tv_choose_patrol_type.visibility = View.GONE
                tv_choose_emergency_type.visibility = View.GONE
                ll_choose_emergency_type.visibility = View.GONE
                ll_choose_patrol_type_line1.visibility = View.GONE
                ll_choose_patrol_type_line2.visibility = View.GONE
            }
        //巡查的案件查询
            "patrol1" -> {
                cv_choose_office_line44.visibility = View.GONE
                ll_choose_emergency_type.visibility = View.GONE
                tv_choose_emergency_type.visibility = View.GONE
                ll_choose_cure_type.visibility = View.GONE
                tv_choose_cure_type.visibility = View.GONE
            }
            "patrol2" -> {
                ll_choose_emergency_type.visibility = View.GONE
                tv_choose_emergency_type.visibility = View.GONE
                ll_choose_cure_type.visibility = View.GONE
                tv_choose_cure_type.visibility = View.GONE
                cv_choose_office_line44.visibility = View.GONE
                tv_choose_rank_grade.visibility = View.GONE
                ll_choose_rank_grade.visibility = View.GONE
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
        choose_condition_back.setOnClickListener { finish() }
        //
        listOfficeViews.forEachIndexed { index1, tv ->
            tv.setOnClickListener {
                hashMapValue["office"] = (it as TextView).text.toString()
                //
                listOfficeCvViews.forEachIndexed { index2, cv ->
                    if (index1 == index2) cv.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
                    else cv.setCardBackgroundColor(Color.parseColor("#efefef"))
                }
            }
        }
        //
        tv_choose_rank1.setOnClickListener {
            cv_choose_rank1.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            hashMapValue["rank"] = tv_choose_rank1.text.toString()
            //
            cv_choose_rank2.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_rank3.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        tv_choose_rank2.setOnClickListener {
            cv_choose_rank2.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            hashMapValue["rank"] = tv_choose_rank2.text.toString()
            //
            cv_choose_rank1.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_rank3.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        tv_choose_rank3.setOnClickListener {
            cv_choose_rank3.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            hashMapValue["rank"] = tv_choose_rank3.text.toString()
            //
            cv_choose_rank1.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_rank2.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        //
        val listPatrolType = mutableListOf(cv_choose_patrol_type_spjl, cv_choose_patrol_type_sjsz, cv_choose_patrol_type_tccxc, cv_choose_patrol_type_gzzxc, cv_choose_patrol_type_lctc, cv_choose_patrol_type_ggfwss)
        tv_choose_patrol_type_spjl.setOnClickListener {
            hashMapValue.clear()
            hashMapValue["type"] = tv_choose_patrol_type_spjl.text.toString()
            //如果是审批掘路，就没有其他选项
            listPatrolType.forEachIndexed { index, cardView ->
                if (index == 0) cardView.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
                else cardView.setCardBackgroundColor(Color.parseColor("#efefef"))
            }
            cv_choose_rank1.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_rank2.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_rank3.setCardBackgroundColor(Color.parseColor("#efefef"))
            listOfficeCvViews.forEach { it.setCardBackgroundColor(Color.parseColor("#efefef")) }
        }
        tv_choose_patrol_type_sjsz.setOnClickListener {
            hashMapValue["type"] = tv_choose_patrol_type_sjsz.text.toString()
            listPatrolType.forEachIndexed { index, cardView ->
                if (index == 1) cardView.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
                else cardView.setCardBackgroundColor(Color.parseColor("#efefef"))
            }
        }
        tv_choose_patrol_type_tccxc.setOnClickListener {
            hashMapValue["type"] = tv_choose_patrol_type_tccxc.text.toString()
            listPatrolType.forEachIndexed { index, cardView ->
                if (index == 2) cardView.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
                else cardView.setCardBackgroundColor(Color.parseColor("#efefef"))
            }
        }
        tv_choose_patrol_type_gzzxc.setOnClickListener {
            hashMapValue["type"] = tv_choose_patrol_type_gzzxc.text.toString()
            listPatrolType.forEachIndexed { index, cardView ->
                if (index == 3) cardView.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
                else cardView.setCardBackgroundColor(Color.parseColor("#efefef"))
            }
        }
        tv_choose_patrol_type_lctc.setOnClickListener {
            hashMapValue["type"] = tv_choose_patrol_type_lctc.text.toString()
            listPatrolType.forEachIndexed { index, cardView ->
                if (index == 4) cardView.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
                else cardView.setCardBackgroundColor(Color.parseColor("#efefef"))
            }
        }
        tv_choose_patrol_type_ggfwss.setOnClickListener {
            hashMapValue["type"] = tv_choose_patrol_type_ggfwss.text.toString()
            listPatrolType.forEachIndexed { index, cardView ->
                if (index == 5) cardView.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
                else cardView.setCardBackgroundColor(Color.parseColor("#efefef"))
            }
        }
        //
        tv_choose_cure_type_dlps.setOnClickListener {
            hashMapValue["type"] = tv_choose_cure_type_dlps.text.toString()
            cv_choose_cure_type_dlps.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            //
            cv_choose_cure_type_bdps.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_cure_type_fsssps.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        tv_choose_cure_type_bdps.setOnClickListener {
            hashMapValue["type"] = tv_choose_cure_type_bdps.text.toString()
            cv_choose_cure_type_bdps.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            //
            cv_choose_cure_type_dlps.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_cure_type_fsssps.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        tv_choose_cure_type_fsssps.setOnClickListener {
            hashMapValue["type"] = tv_choose_cure_type_fsssps.text.toString()
            cv_choose_cure_type_fsssps.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            //
            cv_choose_cure_type_dlps.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_cure_type_bdps.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        //
        tv_choose_emergency_type_dltx.setOnClickListener {
            hashMapValue["type"] = tv_choose_emergency_type_dltx.text.toString()
            cv_choose_emergency_type_dltx.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            //
            cv_choose_emergency_type_gdtx.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        tv_choose_emergency_type_gdtx.setOnClickListener {
            hashMapValue["type"] = tv_choose_emergency_type_gdtx.text.toString()
            cv_choose_emergency_type_gdtx.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            cv_choose_emergency_type_dltx.setCardBackgroundColor(Color.parseColor("#efefef"))
        }
        //
        tv_choose_startdate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(this) {
                tv_choose_startdate.text = it
                hashMapValue["startdate"] = it
            }
        }
        tv_choose_enddate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(this) {
                tv_choose_enddate.text = it
                hashMapValue["enddate"] = it
            }
        }

        //
        //
        cv_choose_office_ok.setOnClickListener {
            val intent = Intent()
            intent.putExtra("condition", hashMapValue)
            setResult(1046, intent)
            finish()
        }
    }

}
