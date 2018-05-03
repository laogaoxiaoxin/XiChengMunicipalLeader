package com.lovelyjiaming.municipalleader.views.fragments.engineer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.EngineerGeneralClass
import com.lovelyjiaming.municipalleader.utils.EngineerMajorClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.EngineerSameAllAdapter
import kotlinx.android.synthetic.main.fragment_engineer_current.*

class EngineerCurrentFragment : Fragment() {
    val adapter: EngineerSameAllAdapter by lazy {
        EngineerSameAllAdapter(activity as Context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_engineer_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engineer_current_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        engineer_current_recyclerview.adapter = adapter
        //default usual
        requestData("General")
        engineer_current_title_general.setOnClickListener {
            engineer_current_title_major.setTextColor(Color.parseColor("#a9a9a9"))
            engineer_current_title_general.setTextColor(Color.parseColor("#DB394A"))
            requestData("General")
        }
        engineer_current_title_major.setOnClickListener {
            engineer_current_title_major.setTextColor(Color.parseColor("#DB394A"))
            engineer_current_title_general.setTextColor(Color.parseColor("#a9a9a9"))
            requestData("Major")
        }
    }

    private fun requestData(type: String) {
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_ENGINEER_ADDRESS + "getOnLineEngineer&projectType=${type}", {
            if (type.equals("General")) {
                val result = Gson().fromJson(it, EngineerGeneralClass::class.java)
                adapter.listData = result.EngineerGeneral
            } else {
                val result = Gson().fromJson(it, EngineerMajorClass::class.java)
                adapter.listData = result.EngineerMajor
            }
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance() = EngineerCurrentFragment()
    }
}
