package com.lovelyjiaming.municipalleader.views.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS
import com.lovelyjiaming.municipalleader.views.adapter.DailyWorkDateItemAdapter
import com.lovelyjiaming.municipalleader.views.adapter.SummaryResult
import kotlinx.android.synthetic.main.activity_daily_work_list.*

class DailyWorkListActivity : AppCompatActivity() {

    private val mAdapter: DailyWorkDateItemAdapter by lazy {
        DailyWorkDateItemAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_work_list)
        AutoUtils.auto(this)
        //
        daily_work_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        daily_work_recyclerview.adapter = mAdapter
        XCNetWorkUtil.invokeGetRequest(this, NETWORK_BASIC_SAVE_ADDRESS + "getSummary", {
            val resultReturn = Gson().fromJson(it, SummaryResult::class.java)
            if (resultReturn.result == 0) {
                Toast.makeText(this, "暂无工作日志", Toast.LENGTH_LONG).show()
                return@invokeGetRequest
            }
            mAdapter.setData(resultReturn.summaryTask)
        }, hashMapOf())
    }
}
