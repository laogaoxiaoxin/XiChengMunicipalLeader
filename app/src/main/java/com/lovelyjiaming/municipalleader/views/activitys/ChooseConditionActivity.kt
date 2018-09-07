package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import kotlinx.android.synthetic.main.activity_choose_condition.*
import java.util.*

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
                //如果是审批掘路的话不能选
                if (hashMapValue["type"] == "审批掘路") {
                    Toast.makeText(this, "当前选择类型是审批掘路，不能选择街道！", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                hashMapValue["office"] = (it as TextView).text.toString()
                //颜色状态
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
            hashMapValue.remove("office")
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
                hashMapValue["startdate"] = it + " 00:00:00"
            }
        }
        tv_choose_enddate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(this) {
                tv_choose_enddate.text = it
                hashMapValue["enddate"] = it + " 24:00:00"
            }
        }

        //
        tv_choose_fix_month.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            //组串
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = if ("${Calendar.getInstance().get(Calendar.MONTH) + 1}".length == 2) Calendar.getInstance().get(Calendar.MONTH) + 1 else "0${Calendar.getInstance().get(Calendar.MONTH) + 1}"
            val lastDay = Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)
            hashMapValue["startdate"] = "$year-$month-01 00:00:00"
            hashMapValue["enddate"] = "$year-$month-$lastDay 23:59:59"
        }
        cv_choose_fix_3month.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            val year = Calendar.getInstance().get(Calendar.YEAR)
            when (getSeason()) {
                1 -> {
                    hashMapValue["startdate"] = "$year-01-01 00:00:00"
                    hashMapValue["enddate"] = "$year-03-31 23:59:59"
                }
                2 -> {
                    hashMapValue["startdate"] = "$year-04-01 00:00:00"
                    hashMapValue["enddate"] = "$year-06-30 23:59:59"
                }
                3 -> {
                    hashMapValue["startdate"] = "$year-07-01 00:00:00"
                    hashMapValue["enddate"] = "$year-09-30 23:59:59"
                }
                4 -> {
                    hashMapValue["startdate"] = "$year-10-01 00:00:00"
                    hashMapValue["enddate"] = "$year-12-31 23:59:59"
                }
            }
        }
        cv_choose_fix_year.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            hashMapValue["startdate"] = "${Calendar.getInstance().get(Calendar.YEAR)}-$01-01 00:00:00"
            hashMapValue["enddate"] = "${Calendar.getInstance().get(Calendar.YEAR)}-$12-31 23:59:59"
        }
        //
        cv_choose_office_ok.setOnClickListener {
            val intent = Intent()
            intent.putExtra("condition", hashMapValue)
            setResult(1046, intent)
            finish()
        }
    }

    private fun getSeason(): Int =
            when (Calendar.getInstance().get(Calendar.MONTH)) {
                Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH -> 1
                Calendar.APRIL, Calendar.MAY, Calendar.JUNE -> 2
                Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER -> 3
                Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER -> 4
                else -> -1
            }


}
