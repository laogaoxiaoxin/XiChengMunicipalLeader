package com.lovelyjiaming.municipalleader.views.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.views.adapter.MoreExamRoadHistoryDailyAdapter
import kotlinx.android.synthetic.main.activity_more_exam_road_history_daily.*

class MoreExamRoadHistoryDailyActivity : AppCompatActivity() {

    private val mAdapter: MoreExamRoadHistoryDailyAdapter by lazy {
        MoreExamRoadHistoryDailyAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_exam_road_history_daily)
        AutoUtils.auto(this)
        //
        back_more_exam_road_history_daily.setOnClickListener { finish() }
        //
        recyclerview_more_exam_road_history_daily.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview_more_exam_road_history_daily.adapter = mAdapter
        mAdapter.setData(g_mapOfMoreDailyInfo)

    }
}
