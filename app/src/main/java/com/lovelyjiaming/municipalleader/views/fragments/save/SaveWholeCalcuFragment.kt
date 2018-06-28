package com.lovelyjiaming.municipalleader.views.fragments.save

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import com.lovelyjiaming.municipalleader.utils.MaterialClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.activitys.ChooseConditionActivity
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
        requestData()
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
        //
        save_whole_top_office.setOnClickListener {
            val intent = Intent(activity, ChooseConditionActivity::class.java)
            intent.putExtra("type", "savecalc")
            startActivityForResult(intent, 3978)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            save_whole_top_office.text = data.getStringExtra("condition") + " "
            requestData()
        }
    }

    private fun requestData() {
        if (!save_whole_top_startdate.text.toString().contains("20") || !save_whole_top_enddate.text.toString().contains("20")) return
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getCaseCount", {
            getMaterielCalc(it)
        }, hashMapOf("startDate" to save_whole_top_startdate.text.toString(), "endDate" to save_whole_top_enddate.text.toString(), "office" to save_whole_top_office.text.toString().trimEnd()))
    }

    private fun getMaterielCalc(result: String) {
        val resultClass = Gson().fromJson(result, MaterialClass::class.java)
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
        adapter.listData?.add("沥青@${allAsphalt}")
        adapter.listData?.add("无机料@${allInorganic}")
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
