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
        val allAsphalt = resultClass.CaseCount[1].taskAsphalt_9cm_10.toFloat().plus(resultClass.CaseCount[1].taskAsphalt_5cm_10.toFloat()).plus(resultClass.CaseCount[1].asphalt_9cm_10_400.toFloat())
                .plus(resultClass.CaseCount[1].asphalt_5cm_10_400.toFloat()).plus(resultClass.CaseCount[1].taskAsphalt_9cm_400.toFloat()).plus(resultClass.CaseCount[1].taskAsphalt_5cm_400.toFloat())

        //无机料加在一起
        val allInorganic = resultClass.CaseCount[1].taskInorganic_material_15cm.toFloat().plus(resultClass.CaseCount[1].taskInorganic_material_15cm.toFloat()).plus(resultClass.CaseCount[1].wujiliao25.toFloat())
//
        val mapParam = HashMap<String, Float>()
        mapParam["沥青"] = allAsphalt
        mapParam["无机料"] = allInorganic
        mapParam[resultClass.CaseCount[0].taskSidewalk] = resultClass.CaseCount[1].taskSidewalk.toFloat()
        mapParam[resultClass.CaseCount[0].mangdao] = resultClass.CaseCount[1].mangdao.toFloat()
        mapParam[resultClass.CaseCount[0].taskPlaster] = resultClass.CaseCount[1].taskPlaster.toFloat()
        mapParam[resultClass.CaseCount[0].caiselumian] = resultClass.CaseCount[1].caiselumian.toFloat()
        mapParam[resultClass.CaseCount[0].shengjiangjianchajing] = resultClass.CaseCount[1].shengjiangjianchajing.toFloat()
        mapParam[resultClass.CaseCount[0].jiagujianchajing] = resultClass.CaseCount[1].jiagujianchajing.toFloat()
        mapParam[resultClass.CaseCount[0].taskRainwater_outlet] = resultClass.CaseCount[1].taskRainwater_outlet.toFloat()
        mapParam[resultClass.CaseCount[0].taskCurb] = resultClass.CaseCount[1].taskCurb.toFloat()
        mapParam[resultClass.CaseCount[0].taskMachine_stone] = resultClass.CaseCount[1].taskMachine_stone.toFloat()
        mapParam[resultClass.CaseCount[0].taskTree_pool] = resultClass.CaseCount[1].taskTree_pool.toFloat()
        mapParam[resultClass.CaseCount[0].shicaibudao] = resultClass.CaseCount[1].shicaibudao.toFloat()
        mapParam[resultClass.CaseCount[0].shicaimangdao] = resultClass.CaseCount[1].shicaimangdao.toFloat()
        mapParam[resultClass.CaseCount[0].shicailuyuanshi] = resultClass.CaseCount[1].shicailuyuanshi.toFloat()
        mapParam[resultClass.CaseCount[0].shicaidangchezhuang] = resultClass.CaseCount[1].shicaidangchezhuang.toFloat()
        mapParam[resultClass.CaseCount[0].tiezhidangchezhuang] = resultClass.CaseCount[1].tiezhidangchezhuang.toFloat()
        mapParam[resultClass.CaseCount[0].taskconcrete] = resultClass.CaseCount[1].taskconcrete.toFloat()
        save_whole_vertical_chart.setAllDatas(mapParam)
        //
        adapter.listData?.clear()
        adapter.listData?.add("沥青@$allAsphalt")
        adapter.listData?.add("无机料@$allInorganic")
        adapter.listData?.add("${resultClass.CaseCount[0].taskSidewalk}@${resultClass.CaseCount[1].taskSidewalk}")
        adapter.listData?.add("${resultClass.CaseCount[0].mangdao}@${resultClass.CaseCount[1].mangdao}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskPlaster}@${resultClass.CaseCount[1].taskPlaster}")
        adapter.listData?.add("${resultClass.CaseCount[0].caiselumian}@${resultClass.CaseCount[1].caiselumian}")
        adapter.listData?.add("${resultClass.CaseCount[0].shengjiangjianchajing}@${resultClass.CaseCount[1].shengjiangjianchajing}")
        adapter.listData?.add("${resultClass.CaseCount[0].jiagujianchajing}@${resultClass.CaseCount[1].jiagujianchajing}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskRainwater_outlet}@${resultClass.CaseCount[1].taskRainwater_outlet}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskCurb}@${resultClass.CaseCount[1].taskCurb}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskMachine_stone}@${resultClass.CaseCount[1].taskMachine_stone}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskTree_pool}@${resultClass.CaseCount[1].taskTree_pool}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicaibudao}@${resultClass.CaseCount[1].shicaibudao}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicaimangdao}@${resultClass.CaseCount[1].shicaimangdao}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicailuyuanshi}@${resultClass.CaseCount[1].shicailuyuanshi}")
        adapter.listData?.add("${resultClass.CaseCount[0].shicaidangchezhuang}@${resultClass.CaseCount[1].shicaidangchezhuang}")
        adapter.listData?.add("${resultClass.CaseCount[0].tiezhidangchezhuang}@${resultClass.CaseCount[1].tiezhidangchezhuang}")
        adapter.listData?.add("${resultClass.CaseCount[0].taskconcrete}@${resultClass.CaseCount[1].taskconcrete}")
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = SaveWholeCalcuFragment()
    }
}
