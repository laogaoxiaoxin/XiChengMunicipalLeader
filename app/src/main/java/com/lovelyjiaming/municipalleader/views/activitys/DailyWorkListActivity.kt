package com.lovelyjiaming.municipalleader.views.activitys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS
import com.lovelyjiaming.municipalleader.views.adapter.DailyWorkAdapter
import com.lovelyjiaming.municipalleader.views.adapter.SummaryResult
import kotlinx.android.synthetic.main.activity_daily_work_list.*

class DailyWorkListActivity : AppCompatActivity() {

    private val mAdapter: DailyWorkAdapter by lazy {
        DailyWorkAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_work_list)
        AutoUtils.auto(this)
        //
        daily_work_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        daily_work_recyclerview.adapter = mAdapter
        XCNetWorkUtil.invokeGetRequest(this, NETWORK_BASIC_SAVE_ADDRESS + "getSummary", {
            val result = Gson().fromJson(it, SummaryResult::class.java)
            mAdapter.setData(result.summaryTask)
        }, hashMapOf())
    }
}
