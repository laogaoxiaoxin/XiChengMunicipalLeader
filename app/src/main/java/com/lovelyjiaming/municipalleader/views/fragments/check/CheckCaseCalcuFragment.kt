package com.lovelyjiaming.municipalleader.views.fragments.check

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import com.lovelyjiaming.municipalleader.utils.InspectCaseCountClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS
import com.lovelyjiaming.municipalleader.views.adapter.CheckCalcuCaseAdapter
import kotlinx.android.synthetic.main.fragment_check_case_calcu.*

class CheckCaseCalcuFragment : Fragment() {

    val adapter: CheckCalcuCaseAdapter by lazy {
        CheckCalcuCaseAdapter(activity as Context)
    }
    //案件类型
    val listCaseType: List<String> = listOf("道路破损", "步道破损", "附属设施破损")
    //街道办事处
    val listRoadAddress: List<String> = listOf("德盛街道", "展览路街道", "新街口街道", "什刹海街道", "月坛街道", "金融街街道", "西长安街街道", "广安门外街道", "广安门内街道", "椿树街道",
            "大栅栏街道", "牛街街道", "白纸坊街道", "陶然亭街道", "天桥街道")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_case_calcu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        check_case_calcu_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        check_case_calcu_recyclerview.adapter = this.adapter
        //
        setClickListener()
    }

    private var popType = 1
    private fun setClickListener() {
        //
        check_case_calcu_startdate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context) { check_case_calcu_startdate.text = it }
        }
        //
        check_case_calcu_enddate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context) {
                check_case_calcu_enddate.text = it
                requestData()
            }
        }
        //
        check_case_calcu_type.setOnClickListener {
            popType = 1
            listview_check_case_calcu_choose.adapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, listCaseType)
            rl_check_case_calcu_choose.visibility = View.VISIBLE
        }
        //
        check_case_calcu_address.setOnClickListener {
            popType = 2
            listview_check_case_calcu_choose.adapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, listRoadAddress)
            rl_check_case_calcu_choose.visibility = View.VISIBLE
        }
        //
        rl_check_case_calcu_choose.setOnClickListener { rl_check_case_calcu_choose.visibility = View.GONE }
        //
        listview_check_case_calcu_choose.setOnItemClickListener { _, _, i, _ ->
            if (popType == 1) check_case_calcu_type.text = listCaseType[i]
            else check_case_calcu_address.text = listRoadAddress[i]
            rl_check_case_calcu_choose.visibility = View.GONE
        }
    }

    private fun requestData() {
        XCNetWorkUtil.invokeGetRequest(activity!!, NETWORK_BASIC_CHECK_ADDRESS + "getCaseCount", {
            val result = Gson().fromJson(it, InspectCaseCountClass::class.java)
            check_case_calcu_ring.setData(result.finished, result.unfinished)
            adapter.listResult = result.InspectCaseCount.toMutableList()
            adapter.notifyDataSetChanged()
        }, hashMapOf("taskType" to check_case_calcu_type.text.toString(), "taskOffice" to check_case_calcu_address.text.toString(), "startDate" to check_case_calcu_startdate.text.toString(),
                "endDate" to check_case_calcu_enddate.text.toString()))
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    companion object {
        fun newInstance() = CheckCaseCalcuFragment()
    }
}
