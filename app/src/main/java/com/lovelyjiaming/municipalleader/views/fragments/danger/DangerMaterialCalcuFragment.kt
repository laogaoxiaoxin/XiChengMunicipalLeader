package com.lovelyjiaming.municipalleader.views.fragments.danger

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.*
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.invokeGetRequest
import com.lovelyjiaming.municipalleader.views.adapter.SaveWholeCalcuAdapter
import kotlinx.android.synthetic.main.fragment_danger_material_calcu.*

class DangerMaterialCalcuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_danger_material_calcu, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) requestData("Car")
    }

    private fun setClickListener() {
        danger_title_material_car.setOnClickListener {
            requestData("Car")
            danger_title_material_car.setTextColor(Color.parseColor("#DB394A"))
            danger_title_material_mach.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_smallmach.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_supply.setTextColor(Color.parseColor("#a4a4a4"))

        }
        danger_title_material_mach.setOnClickListener {
            requestData("Mech")
            danger_title_material_mach.setTextColor(Color.parseColor("#DB394A"))
            danger_title_material_car.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_smallmach.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_supply.setTextColor(Color.parseColor("#a4a4a4"))
        }
        danger_title_material_smallmach.setOnClickListener {
            requestData("smallMech")
            danger_title_material_smallmach.setTextColor(Color.parseColor("#DB394A"))
            danger_title_material_car.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_supply.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_mach.setTextColor(Color.parseColor("#a4a4a4"))

        }
        danger_title_material_supply.setOnClickListener {
            requestData("Suppilies")
            danger_title_material_supply.setTextColor(Color.parseColor("#DB394A"))
            danger_title_material_car.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_mach.setTextColor(Color.parseColor("#a4a4a4"))
            danger_title_material_smallmach.setTextColor(Color.parseColor("#a4a4a4"))
        }
    }

    fun requestData(type: String) {
        val mapDatas = hashMapOf<String, Int>()
        invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_DANGER_ADDRESS + "getSupplyCount&supplyType=$type", {
            when (type) {
                "Car" -> {
                    val result = Gson().fromJson(it, EmergencyCarClass::class.java)
                    val type1 = result.EmergencyCar.filter { it.carType == "双排" }
                    mapDatas.put("双排", type1.size)
                    val type2 = result.EmergencyCar.filter { it.carType == "小工程车" }
                    mapDatas.put("小工程车", type2.size)
                    val type3 = result.EmergencyCar.filter { it.carType == "大工程车" }
                    mapDatas.put("大工程车", type3.size)
                    val type4 = result.EmergencyCar.filter { it.carType == "东风大板" }
                    mapDatas.put("东风大板", type4.size)
                    val type5 = result.EmergencyCar.filter { it.carType == "大水车" }
                    mapDatas.put("大水车", type5.size)
                    val type6 = result.EmergencyCar.filter { it.carType == "小水车" }
                    mapDatas.put("小水车", type6.size)
                    val type7 = result.EmergencyCar.filter { it.carType == "高空作业车" }
                    mapDatas.put("高空作业车", type7.size)
                    val type8 = result.EmergencyCar.filter { it.carType == "平板" }
                    mapDatas.put("平板", type8.size)
                    val type9 = result.EmergencyCar.filter { it.carType == "厢式货车" }
                    mapDatas.put("厢式货车", type9.size)
                    val type10 = result.EmergencyCar.filter { it.carType == "单排" }
                    mapDatas.put("单排", type10.size)
                    val type11 = result.EmergencyCar.filter { it.carType == "金杯" }
                    mapDatas.put("金杯", type11.size)
                    val type12 = result.EmergencyCar.filter { it.carType == "福田自卸" }
                    mapDatas.put("福田自卸", type12.size)
                    danger_type_calc_column.setAllDatas(mapDatas)
                }
            //-----
                "Mech" -> {
                    val result = Gson().fromJson(it, EmergencyMechClass::class.java)
                    val type1 = result.EmergencyMech.filter { it.carType == "DH130W-V" }
                    mapDatas["DH130W-V"] = type1.size
                    val type2 = result.EmergencyMech.filter { it.carType == "CC142" }
                    mapDatas["CC142"] = type2.size
                    val type3 = result.EmergencyMech.filter { it.carType == "CC900" }
                    mapDatas["CC900"] = type3.size
                    val type4 = result.EmergencyMech.filter { it.carType == "W50" }
                    mapDatas.put("W50", type4.size)
                    val type5 = result.EmergencyMech.filter { it.carType == "S300-1" }
                    mapDatas["S300-1"] = type5.size
                    val type6 = result.EmergencyMech.filter { it.carType == "HML32" }
                    mapDatas["HML32"] = type6.size
                    val type7 = result.EmergencyMech.filter { it.carType == "8061" }
                    mapDatas.put("8061", type7.size)
                    val type8 = result.EmergencyMech.filter { it.carType == "XG916" }
                    mapDatas.put("XG916", type8.size)
                    val type9 = result.EmergencyMech.filter { it.carType == "CPC35" }
                    mapDatas.put("CPC35", type9.size)
                    val type10 = result.EmergencyMech.filter { it.carType == "DX150" }
                    mapDatas.put("DX150", type10.size)
                    val type11 = result.EmergencyMech.filter { it.carType == "SR250" }
                    mapDatas.put("SR250", type11.size)
                    val type12 = result.EmergencyMech.filter { it.carType == "W50H" }
                    mapDatas.put("W50H", type12.size)
                    danger_type_calc_column.setAllDatas(mapDatas)
                }
            //-----
                "smallMech" -> {
                    val result = Gson().fromJson(it, EmergencysmallMechClass::class.java)
                    result.EmergencysmallMech.forEachIndexed { index, emergencysmallMechItem ->
                        mapDatas.put(emergencysmallMechItem.DeviceName!!, emergencysmallMechItem.Number?.replace("台", "")?.toInt()!!)
                    }
                    danger_type_calc_column.setAllDatas(mapDatas)
                }
            //-----
                "Suppilies" -> {
                    val result = Gson().fromJson(it, EmergencySuppiliesClass::class.java)
                    result.EmergencySuppilies.forEachIndexed { index, emergencySuppiliesItem ->
                        mapDatas.put(emergencySuppiliesItem.SupplyName!!, emergencySuppiliesItem.Number?.replace("袋", "")?.replace("个", "")?.replace("块", "")?.replace("吨", "")?.replace("包", "")?.toInt()!!)
                    }
                    danger_type_calc_column.setAllDatas(mapDatas)
                }
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        danger_material_calc_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        danger_material_calc_recyclerview.adapter = SaveWholeCalcuAdapter(activity as Context)
        setClickListener()
    }

    companion object {
        fun newInstance() = DangerMaterialCalcuFragment()
    }
}
