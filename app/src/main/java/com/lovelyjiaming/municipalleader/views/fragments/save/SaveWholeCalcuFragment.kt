package com.lovelyjiaming.municipalleader.views.fragments.save

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.MaterialClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.activitys.ChooseConditionActivity
import com.lovelyjiaming.municipalleader.views.adapter.SaveWholeCalcuAdapter
import kotlinx.android.synthetic.main.fragment_save_whole_calcu.*
import java.text.SimpleDateFormat
import java.util.*

class SaveWholeCalcuFragment : Fragment() {

    val adapter: SaveWholeCalcuAdapter by lazy {
        SaveWholeCalcuAdapter(activity as Context)
    }
    var startDate = ""
    var endDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_whole_calcu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_whole_recyclerview.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        save_whole_recyclerview.adapter = adapter
        requestData()
        //
        save_whole_top_office.setOnClickListener {
            val intent = Intent(activity, ChooseConditionActivity::class.java)
            intent.putExtra("type", "savecalc")
            startActivityForResult(intent, 3978)
        }
        //
        //
        cv_choose_fix_month.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#efefef"))
            //组串
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = if ("${Calendar.getInstance().get(Calendar.MONTH) + 1}".length == 2) Calendar.getInstance().get(Calendar.MONTH) + 1 else "0${Calendar.getInstance().get(Calendar.MONTH) + 1}"
            val lastDay = Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)
            startDate = "$year-$month-01"
            endDate = "$year-$month-$lastDay"
            requestData()

        }
        //
        cv_choose_fix_3month.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            val year = Calendar.getInstance().get(Calendar.YEAR)
            when (getSeason()) {
                1 -> {
                    startDate = "$year-01-01"
                    endDate = "$year-03-31"
                }
                2 -> {
                    startDate = "$year-04-01"
                    endDate = "$year-06-30"
                }
                3 -> {
                    startDate = "$year-07-01"
                    endDate = "$year-09-30"
                }
                4 -> {
                    startDate = "$year-10-01"
                    endDate = "$year-12-31"
                }
            }
            requestData()

        }
        //
        cv_choose_fix_year.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            startDate = "${Calendar.getInstance().get(Calendar.YEAR)}-01-01"
            endDate = "${Calendar.getInstance().get(Calendar.YEAR)}-12-31"
            requestData()

        }
        //
        cv_choose_fix_week.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            //
            val sdf = SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
            val nowCal = Calendar.getInstance()
            val dayWeek = nowCal.get(Calendar.DAY_OF_WEEK)
            if (1 == dayWeek) {
                nowCal.add(Calendar.DAY_OF_MONTH, -1);
            }
            nowCal.firstDayOfWeek = Calendar.MONDAY;//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            val day = nowCal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
            nowCal.add(Calendar.DATE, nowCal.firstDayOfWeek - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            startDate = sdf.format(nowCal.time)
            nowCal.add(Calendar.DATE, 6);
            endDate = sdf.format(nowCal.time)
            requestData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            save_whole_top_office.text = (data.getSerializableExtra("condition") as MutableMap<String, String>)["office"]
            requestData()
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

    private fun requestData() {
        if (!startDate.contains("20") || !endDate.contains("20")) return
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getCaseCount", {
            getMaterielCalc(it)
        }, hashMapOf("startDate" to startDate, "endDate" to endDate, "office" to save_whole_top_office.text.toString().trimEnd()))
    }

    private fun getMaterielCalc(result: String) {
        val resultClass = Gson().fromJson(result, MaterialClass::class.java)
        if (resultClass.CaseCount.size == 1) return
        //
        //沥青加一起
        val allAsphalt = ((formatNullStr(resultClass.CaseCount[1].taskAsphalt_9cm_10)
                ?: "0.0").toFloat()).plus((formatNullStr(resultClass.CaseCount[1].taskAsphalt_5cm_10)
                ?: "0.0").toFloat()).plus((formatNullStr(resultClass.CaseCount[1].asphalt_5cm_10_400)
                ?: "0.0").toFloat()).plus((formatNullStr(resultClass.CaseCount[1].asphalt_9cm_10_400)
                ?: "0.0").toFloat()).plus((formatNullStr(resultClass.CaseCount[1].taskAsphalt_5cm_400)
                ?: "0.0").toFloat()).plus((formatNullStr(resultClass.CaseCount[1].taskAsphalt_9cm_400)
                ?: "0.0").toFloat())

        //无机料加在一起
        val allInorganic = ((formatNullStr(resultClass.CaseCount[1].taskInorganic_material_15cm)
                ?: "0.0").toFloat()).plus((formatNullStr(resultClass.CaseCount[1].wujiliao25)
                ?: "0.0").toFloat()).plus((formatNullStr(resultClass.CaseCount[1].taskInorganic_material_20cm)
                ?: "0.0").toFloat())
//
        val mapParam = hashMapOf<String, Float>()
        mapParam["沥青"] = allAsphalt
        mapParam["无机料"] = allInorganic
        mapParam[resultClass.CaseCount[0].taskSidewalk!!] = (formatNullStr(resultClass.CaseCount[1].taskSidewalk)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].mangdao!!] = (formatNullStr(resultClass.CaseCount[1].mangdao)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].taskPlaster!!] = (formatNullStr(resultClass.CaseCount[1].taskPlaster)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].caiselumian!!] = (formatNullStr(resultClass.CaseCount[1].caiselumian)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].shengjiangjianchajing!!] = (formatNullStr(resultClass.CaseCount[1].shengjiangjianchajing)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].jiagujianchajing!!] = (formatNullStr(resultClass.CaseCount[1].jiagujianchajing)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].taskRainwater_outlet!!] = (formatNullStr(resultClass.CaseCount[1].taskRainwater_outlet)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].taskCurb!!] = (formatNullStr(resultClass.CaseCount[1].taskCurb)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].taskMachine_stone!!] = (formatNullStr(resultClass.CaseCount[1].taskMachine_stone)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].taskTree_pool!!] = (formatNullStr(resultClass.CaseCount[1].taskTree_pool)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].shicaibudao!!] = (formatNullStr(resultClass.CaseCount[1].shicaibudao)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].shicaimangdao!!] = (formatNullStr(resultClass.CaseCount[1].shicaimangdao)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].shicailuyuanshi!!] = (formatNullStr(resultClass.CaseCount[1].shicailuyuanshi)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].shicaidangchezhuang!!] = (formatNullStr(resultClass.CaseCount[1].shicaidangchezhuang)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].tiezhidangchezhuang!!] = (formatNullStr(resultClass.CaseCount[1].tiezhidangchezhuang)
                ?: "0.0").toFloat()
        mapParam[resultClass.CaseCount[0].taskconcrete!!] = (formatNullStr(resultClass.CaseCount[1].taskconcrete)
                ?: "0.0").toFloat()
        save_whole_vertical_chart.setAllDatas(mapParam)
        //
        adapter.listData?.clear()
        adapter.listData?.add("沥青@${substringFloat2(allAsphalt.toString())}")
        adapter.listData?.add("无机料@${substringFloat2(allInorganic.toString())}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskSidewalk}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].taskSidewalk)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].mangdao}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].mangdao)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskPlaster}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].taskPlaster)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].caiselumian}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].caiselumian)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].shengjiangjianchajing}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].shengjiangjianchajing)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].jiagujianchajing}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].jiagujianchajing)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskRainwater_outlet}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].taskRainwater_outlet)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskCurb}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].taskCurb)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskMachine_stone}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].taskMachine_stone)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskTree_pool}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].taskTree_pool)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicaibudao}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].shicaibudao)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicaimangdao}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].shicaimangdao)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicailuyuanshi}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].shicailuyuanshi)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicaidangchezhuang}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].shicaidangchezhuang)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].tiezhidangchezhuang}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].tiezhidangchezhuang)
                ?: "0.0")}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskconcrete}@${substringFloat2(formatNullStr(resultClass.CaseCount[1].taskconcrete)
                ?: "0.0")}")
        adapter.notifyDataSetChanged()
    }

    private fun substringFloat2(strIn: String): String {
        if (strIn.contains(".") && strIn.substringAfterLast(".").length > 2)
            return strIn.substringBefore(".") + "." + strIn.substringAfter(".").substring(0, 1)
        return strIn
    }

    private fun formatNullStr(strIn: String?): String {
        if (strIn == null || strIn == "null")
            return "0.0"
        return strIn
    }

    companion object {
        fun newInstance() = SaveWholeCalcuFragment()
    }
}
