package com.lovelyjiaming.municipalleader.views.fragments.engineer


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import com.lovelyjiaming.municipalleader.utils.EngineerGeneralClass
import com.lovelyjiaming.municipalleader.utils.EngineerMajorClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.EngineerSameAllAdapter
import kotlinx.android.synthetic.main.fragment_engineer_end.*

class EngineerEndFragment : Fragment() {
    val adapter: EngineerSameAllAdapter by lazy {
        EngineerSameAllAdapter(activity as Context)
    }
    private var strEndTime: String = ""
    private var strStartTime: String = ""
    private var strTitleType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_engineer_end, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engineer_end_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        engineer_end_recyclerview.adapter = adapter
        //
        engineer_end_starttime.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context, {
                strStartTime = it
                engineer_end_starttime.text = it
            })
        }
        engineer_end_endtime.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context, {
                strEndTime = it
                engineer_end_endtime.text = it
                requestData()
            })
        }
        engineer_end_title_general.setOnClickListener {
            if (strStartTime.isEmpty() || strEndTime.isEmpty()) {
                Toast.makeText(activity, "请先选择开始和结束时间", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            engineer_end_title_major.setTextColor(Color.parseColor("#a9a9a9"))
            engineer_end_title_general.setTextColor(Color.parseColor("#DB394A"))
            strTitleType = "General"
            requestData()
        }
        engineer_end_title_major.setOnClickListener {
            if (strStartTime.isEmpty() || strEndTime.isEmpty()) {
                Toast.makeText(activity, "请先选择开始和结束时间", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            engineer_end_title_major.setTextColor(Color.parseColor("#DB394A"))
            engineer_end_title_general.setTextColor(Color.parseColor("#a9a9a9"))
            strTitleType = "Major"
            requestData()
        }
    }

    private fun requestData() {
        if (strTitleType.isEmpty()) return
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_ENGINEER_ADDRESS + "getDoneEngineer&projectType=${strTitleType}", {
            if (strTitleType.equals("General")) {
                val result = Gson().fromJson(it, EngineerGeneralClass::class.java)
                adapter.listData = result.EngineerGeneral
                adapter.notifyDataSetChanged()
            } else {
                val result = Gson().fromJson(it, EngineerMajorClass::class.java)
                adapter.listData = result.EngineerMajor
                adapter.notifyDataSetChanged()
            }
        }, hashMapOf("startDate" to strStartTime, "endDate" to strEndTime))
    }

    companion object {
        fun newInstance() =
                EngineerEndFragment()
    }
}
