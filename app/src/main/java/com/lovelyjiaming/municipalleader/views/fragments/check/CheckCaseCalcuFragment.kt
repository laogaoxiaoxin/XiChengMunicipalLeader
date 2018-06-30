package com.lovelyjiaming.municipalleader.views.fragments.check

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.R.id.*
import com.lovelyjiaming.municipalleader.utils.InspectCaseCountClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS
import com.lovelyjiaming.municipalleader.views.adapter.CheckCalcuCaseAdapter
import kotlinx.android.synthetic.main.common_top_del_condition.*
import kotlinx.android.synthetic.main.fragment_check_case_calcu.*

class CheckCaseCalcuFragment : Fragment() {

    val adapter: CheckCalcuCaseAdapter by lazy {
        CheckCalcuCaseAdapter(activity as Context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_case_calcu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        check_case_calcu_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        check_case_calcu_recyclerview.adapter = this.adapter
    }

    private fun requestData(office: String, type: String, startdate: String, enddate: String) {
        XCNetWorkUtil.invokeGetRequest(activity!!, NETWORK_BASIC_CHECK_ADDRESS + "getCaseCount", {
            if (it.contains("result: 0")) return@invokeGetRequest
            val result = Gson().fromJson(it, InspectCaseCountClass::class.java)
            check_case_calcu_ring.setData(result.finished, result.unfinished)
            adapter.listResult = result.InspectCaseCount?.toMutableList()
            adapter.notifyDataSetChanged()
        }, hashMapOf("taskType" to type, "taskOffice" to office, "startDate" to startdate, "endDate" to enddate))
    }

    private lateinit var mMapCondition: MutableMap<String, String>
    //
    fun startSearch(mapCondition: MutableMap<String, String>) {
        this.mMapCondition = mapCondition
        ll_check_case_calcu_select.visibility = View.VISIBLE
        var office = ""
        var type = ""
        var startdate = ""
        var enddate = ""
        //
        if (mMapCondition.containsKey("office")) {
            first_del_condition.text = mMapCondition["office"]
            office = mMapCondition["office"] ?: ""
            first_del_condition.visibility = View.VISIBLE
        }
        if (mMapCondition.containsKey("type")) {
            second_del_condition.text = mMapCondition["type"]
            type = mMapCondition["type"] ?: ""
            second_del_condition.visibility = View.VISIBLE
        }
        if (mMapCondition.containsKey("startdate")) {
            third_del_condition.text = mMapCondition["startdate"]
            startdate = mMapCondition["startdate"] ?: ""
            third_del_condition.visibility = View.VISIBLE
        }
        if (mMapCondition.containsKey("enddate")) {
            fourth_del_condition.text = mMapCondition["enddate"]
            enddate = mMapCondition["enddate"] ?: ""
            fourth_del_condition.visibility = View.VISIBLE
        }
        requestData(office, type, startdate, enddate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        XCNetWorkUtil.cancelRequest()
    }

    companion object {
        fun newInstance() = CheckCaseCalcuFragment()
    }
}
