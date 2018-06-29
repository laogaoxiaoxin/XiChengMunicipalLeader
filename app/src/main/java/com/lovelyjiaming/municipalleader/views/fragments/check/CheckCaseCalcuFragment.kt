package com.lovelyjiaming.municipalleader.views.fragments.check

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.R.id.*
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

    private fun requestData() {
//        XCNetWorkUtil.invokeGetRequest(activity!!, NETWORK_BASIC_CHECK_ADDRESS + "getCaseCount", {
//            val result = Gson().fromJson(it, InspectCaseCountClass::class.java)
//            check_case_calcu_ring.setData(result.finished, result.unfinished)
//            adapter.listResult = result.InspectCaseCount?.toMutableList()
//            adapter.notifyDataSetChanged()
//        }, hashMapOf("taskType" to check_case_calcu_type.text.toString(), "taskOffice" to check_case_calcu_address.text.toString(), "startDate" to check_case_calcu_startdate.text.toString(),
//                "endDate" to check_case_calcu_enddate.text.toString()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        XCNetWorkUtil.cancelRequest()
    }

    companion object {
        fun newInstance() = CheckCaseCalcuFragment()
    }
}
