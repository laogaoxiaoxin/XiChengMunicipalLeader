package com.lovelyjiaming.municipalleader.views.fragments.danger

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.lovelyjiaming.municipalleader.R
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.utils.EmergencyTask
import com.lovelyjiaming.municipalleader.utils.EmergencyTaskItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.DangerRushCaseAdapter
import kotlinx.android.synthetic.main.common_search_layout.*
import kotlinx.android.synthetic.main.common_top_del_condition.*
import kotlinx.android.synthetic.main.fragment_danger_case.*
import java.text.SimpleDateFormat

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
        danger_rush_case_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        danger_rush_case_recyclerview.adapter = adapter
        //
        danger_rush_case_swiperefresh.isRefreshing = true
        requestData()
        danger_rush_case_swiperefresh.setOnRefreshListener {
            requestData()
        }
        //
        edt_search_condition.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //如果已经筛选过一次了，比如按照tasktype筛过一次了
                val list = if (mFilterDetailInfoList != null && mFilterDetailInfoList!!.size > 0) {
                    mFilterDetailInfoList?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                } else
                    mEmergencyDetailList?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                //
                adapter.listData = list
                mParentFragment.displayCaseCount(list?.size ?: 0)
                adapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //
        val arrayDelView = arrayListOf(first_del_condition, second_del_condition, third_del_condition, fourth_del_condition)
        arrayDelView.forEach { it1 ->
            it1.setOnClickListener { _ ->
                it1.visibility = View.GONE
                if (it1.text.toString().startsWith("20")) {
                    mMapCondition.remove("startdate")
                    mMapCondition.remove("enddate")
                    startSearch(mMapCondition)
                } else
                    startSearch(mMapCondition.filterValues { it != it1.text.toString().replace("X", "").trim() } as MutableMap<String, String>)
            }
        }
    }

    private var mEmergencyDetailList: MutableList<EmergencyTaskItemClass>? = null
    private var mFilterDetailInfoList: MutableList<EmergencyTaskItemClass>? = null

    private fun requestData() {
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_DANGER_ADDRESS + "getOnLineTask", {
            val result = Gson().fromJson(it, EmergencyTask::class.java)
            mEmergencyDetailList = result.EmergencyTask.toMutableList()
            //
            mParentFragment.displayCaseCount(mEmergencyDetailList?.size ?: 0)
            adapter.listData = mEmergencyDetailList
            adapter.notifyDataSetChanged()
            danger_rush_case_swiperefresh.isRefreshing = false
        })
    }

    private lateinit var mMapCondition: MutableMap<String, String>
    @SuppressLint("SetTextI18n")
    fun startSearch(condition: MutableMap<String, String>) {
        //
        mMapCondition = condition
        //缓存，保证成员变量mDetailInfoList总是完整的
        var localTmpDetailInfo = mEmergencyDetailList
        //
        first_del_condition.visibility = View.GONE
        second_del_condition.visibility = View.GONE
        third_del_condition.visibility = View.GONE
        fourth_del_condition.visibility = View.GONE
        danger_case_del_view.visibility = View.VISIBLE
        //
        if (condition.containsKey("startdate") && condition.containsKey("enddate")) {
            //比对时间
            val spFormat = SimpleDateFormat("yyyy-MM-dd")
            val start = spFormat.parse(condition["startdate"])
            val end = spFormat.parse(condition["enddate"])
            localTmpDetailInfo = localTmpDetailInfo?.filter { start.time <= spFormat.parse(it.taskDate).time && spFormat.parse(it.taskDate).time <= end.time }?.toMutableList()
            //
            fourth_del_condition.text = condition["startdate"] + "~" + condition["enddate"] + "   X"
            fourth_del_condition.visibility = View.VISIBLE
            danger_case_del_view.visibility = View.VISIBLE
        }
        //
        if (condition.containsKey("type") && condition.containsKey("office")) {
            mFilterDetailInfoList = localTmpDetailInfo?.filter { it.taskType?.contains(condition["type"].toString())!! && it.taskOffice?.contains(condition["office"].toString())!! }?.toMutableList()
            //
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
            first_del_condition.text = condition["type"] + "   X"
            second_del_condition.text = condition["office"] + "   X"
        } else if (condition.containsKey("type")) {
            mFilterDetailInfoList = localTmpDetailInfo?.filter { it.taskType?.contains(condition["type"].toString())!! }?.toMutableList()
            first_del_condition.visibility = View.VISIBLE
            first_del_condition.text = condition["type"] + "   X"
        } else if (condition.containsKey("office")) {
            mFilterDetailInfoList = localTmpDetailInfo?.filter { it.taskOffice?.contains(condition["office"].toString())!! }?.toMutableList()
            first_del_condition.visibility = View.VISIBLE
            first_del_condition.text = condition["office"] + "   X"
        } else {
            mFilterDetailInfoList = localTmpDetailInfo
            if (mMapCondition.isEmpty()) danger_case_del_view.visibility = View.GONE
        }
        mParentFragment.displayCaseCount(mFilterDetailInfoList?.size ?: 0)
        adapter.listData = mFilterDetailInfoList
        adapter.notifyDataSetChanged()
        //
        Toast.makeText(activity, "共查找出案件${mFilterDetailInfoList?.size
                ?: 0}件", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private lateinit var mParentFragment: DangerFragment
        fun newInstance(fragment: DangerFragment): DangerRushCaseFragment {
            mParentFragment = fragment
            return DangerRushCaseFragment()
        }
    }
}

