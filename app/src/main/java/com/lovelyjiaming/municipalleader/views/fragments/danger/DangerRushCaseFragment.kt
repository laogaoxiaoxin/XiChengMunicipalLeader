package com.lovelyjiaming.municipalleader.views.fragments.danger


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
import com.lovelyjiaming.municipalleader.utils.EmergencyTask
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.DangerRushCaseAdapter
import kotlinx.android.synthetic.main.fragment_danger_case.*

class DangerRushCaseFragment : Fragment() {

    val adapter: DangerRushCaseAdapter by lazy {
        DangerRushCaseAdapter(activity as Context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_danger_case, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        danger_case_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        danger_case_recyclerview.adapter = adapter
        //
        requestData()
    }

    fun requestData() {
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_DANGER_ADDRESS + "getOnLineTask", {
            val result = Gson().fromJson(it, EmergencyTask::class.java)
            adapter.listData = result.EmergencyTask
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance() = DangerRushCaseFragment()
    }
}

