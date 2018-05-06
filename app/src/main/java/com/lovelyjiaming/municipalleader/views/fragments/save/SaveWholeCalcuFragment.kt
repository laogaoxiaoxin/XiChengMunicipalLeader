package com.lovelyjiaming.municipalleader.views.fragments.save

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.R.id.save_whole_vertical_chart
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import com.lovelyjiaming.municipalleader.utils.MaterailListClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.SaveWholeCalcuAdapter
import kotlinx.android.synthetic.main.fragment_save_whole_calcu.*

class SaveWholeCalcuFragment : Fragment() {

    val adapter: SaveWholeCalcuAdapter by lazy {
        SaveWholeCalcuAdapter(activity as Context)
    }

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
        //
        save_whole_top_startdate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context, {
                save_whole_top_startdate.text = it
                requestData()
            })
        }
        save_whole_top_enddate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context, {
                save_whole_top_enddate.text = it
                requestData()
            })
        }
    }

    fun requestData() {
        if (!save_whole_top_startdate.text.toString().contains("20") || !save_whole_top_enddate.text.toString().contains("20")) return
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getCaseCount", {
            getMetrailCalc(it)
        }, hashMapOf("startDate" to save_whole_top_startdate.text.toString(), "endDate" to save_whole_top_enddate.text.toString()))
    }

    fun getMetrailCalc(result: String) {
        //
        val result = Gson().fromJson(result, MaterailListClass::class.java)
        //沥青加一起
        var allAsphalt = result.MaterailCountList[0].taskAsphalt_9cm_10.toFloat().plus(result.MaterailCountList[0].taskAsphalt_5cm_10.toFloat()).plus(result.MaterailCountList[0].asphalt_9cm_10_400.toFloat())
                .plus(result.MaterailCountList[0].asphalt_5cm_10_400.toFloat()).plus(result.MaterailCountList[0].taskAsphalt_9cm_400.toFloat()).plus(result.MaterailCountList[0].taskAsphalt_5cm_400.toFloat())

        //无机料加在一起
        val allInorganic = result.MaterailCountList[0].taskInorganic_material_15cm.toFloat().plus(result.MaterailCountList[0].taskInorganic_material_15cm.toFloat()).plus(result.MaterailCountList[0].wujiliao25.toFloat())

        val mapParam = HashMap<String, Float>()
        mapParam.put("沥青", allAsphalt)
        mapParam.put("无机料", allInorganic)
        mapParam.put(result.MaterailTitleList[0].taskSidewalk, result.MaterailCountList[0].taskSidewalk.toFloat())
        mapParam.put(result.MaterailTitleList[0].mangdao, result.MaterailCountList[0].mangdao.toFloat())
        mapParam.put(result.MaterailTitleList[0].taskPlaster, result.MaterailCountList[0].taskPlaster.toFloat())
        mapParam.put(result.MaterailTitleList[0].caiselumian, result.MaterailCountList[0].caiselumian.toFloat())
        mapParam.put(result.MaterailTitleList[0].shengjiangjianchajing, result.MaterailCountList[0].shengjiangjianchajing.toFloat())
        mapParam.put(result.MaterailTitleList[0].jiagujianchajing, result.MaterailCountList[0].jiagujianchajing.toFloat())
        mapParam.put(result.MaterailTitleList[0].taskRainwater_outlet, result.MaterailCountList[0].taskRainwater_outlet.toFloat())
        mapParam.put(result.MaterailTitleList[0].taskCurb, result.MaterailCountList[0].taskCurb.toFloat())
        mapParam.put(result.MaterailTitleList[0].taskMachine_stone, result.MaterailCountList[0].taskMachine_stone.toFloat())
        mapParam.put(result.MaterailTitleList[0].taskTree_pool, result.MaterailCountList[0].taskTree_pool.toFloat())
        mapParam.put(result.MaterailTitleList[0].shicaibudao, result.MaterailCountList[0].shicaibudao.toFloat())
        mapParam.put(result.MaterailTitleList[0].shicaimangdao, result.MaterailCountList[0].shicaimangdao.toFloat())
        mapParam.put(result.MaterailTitleList[0].shicailuyuanshi, result.MaterailCountList[0].shicailuyuanshi.toFloat())
        mapParam.put(result.MaterailTitleList[0].shicaidangchezhuang, result.MaterailCountList[0].shicaidangchezhuang.toFloat())
        mapParam.put(result.MaterailTitleList[0].tiezhidangchezhuang, result.MaterailCountList[0].tiezhidangchezhuang.toFloat())
        mapParam.put(result.MaterailTitleList[0].taskconcrete, result.MaterailCountList[0].taskconcrete.toFloat())
        save_whole_vertical_chart.setAllDatas(mapParam)
        //
        adapter.listData?.add("沥青@${allAsphalt}")
        adapter.listData?.add("无机料@${allInorganic}")
        adapter.listData?.add("${result.MaterailTitleList[0].taskSidewalk}@${result.MaterailCountList[0].taskSidewalk}")
        adapter.listData?.add("${result.MaterailTitleList[0].mangdao}@${result.MaterailCountList[0].mangdao}")
        adapter.listData?.add("${result.MaterailTitleList[0].taskPlaster}@${result.MaterailCountList[0].taskPlaster}")
        adapter.listData?.add("${result.MaterailTitleList[0].caiselumian}@${result.MaterailCountList[0].caiselumian}")
        adapter.listData?.add("${result.MaterailTitleList[0].shengjiangjianchajing}@${result.MaterailCountList[0].shengjiangjianchajing}")
        adapter.listData?.add("${result.MaterailTitleList[0].jiagujianchajing}@${result.MaterailCountList[0].jiagujianchajing}")
        adapter.listData?.add("${result.MaterailTitleList[0].taskRainwater_outlet}@${result.MaterailCountList[0].taskRainwater_outlet}")
        adapter.listData?.add("${result.MaterailTitleList[0].taskCurb}@${result.MaterailCountList[0].taskCurb}")
        adapter.listData?.add("${result.MaterailTitleList[0].taskMachine_stone}@${result.MaterailCountList[0].taskMachine_stone}")
        adapter.listData?.add("${result.MaterailTitleList[0].taskTree_pool}@${result.MaterailCountList[0].taskTree_pool}")
        adapter.listData?.add("${result.MaterailTitleList[0].shicaibudao}@${result.MaterailCountList[0].shicaibudao}")
        adapter.listData?.add("${result.MaterailTitleList[0].shicaimangdao}@${result.MaterailCountList[0].shicaimangdao}")
        adapter.listData?.add("${result.MaterailTitleList[0].shicailuyuanshi}@${result.MaterailCountList[0].shicailuyuanshi}")
        adapter.listData?.add("${result.MaterailTitleList[0].shicaidangchezhuang}@${result.MaterailCountList[0].shicaidangchezhuang}")
        adapter.listData?.add("${result.MaterailTitleList[0].tiezhidangchezhuang}@${result.MaterailCountList[0].tiezhidangchezhuang}")
        adapter.listData?.add("${result.MaterailTitleList[0].taskconcrete}@${result.MaterailCountList[0].taskconcrete}")
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = SaveWholeCalcuFragment()
    }
}
