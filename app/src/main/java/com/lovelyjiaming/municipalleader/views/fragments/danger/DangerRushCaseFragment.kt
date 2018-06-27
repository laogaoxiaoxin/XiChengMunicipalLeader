package com.lovelyjiaming.municipalleader.views.fragments.danger

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.R.id.danger_rush_case_swiperefresh
import com.lovelyjiaming.municipalleader.utils.EmergencyTask
import com.lovelyjiaming.municipalleader.utils.EmergencyTaskItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.DangerRushCaseAdapter
import com.lovelyjiaming.municipalleader.views.fragments.save.SaveCurrentTaskFragment
import kotlinx.android.synthetic.main.common_search_layout.*
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

    fun startSearch(condition: String) {
        when (condition) {
            "道路塌陷", "管道塌陷" ->
                mFilterDetailInfoList = mEmergencyDetailList?.filter { it.taskType?.contains(condition)!! }?.toMutableList()
            else -> {
                if (condition.contains("街道")) {
                    mFilterDetailInfoList = mEmergencyDetailList?.filter { it.taskOffice?.contains(condition)!! }?.toMutableList()
                }
            }
        }
        mParentFragment.displayCaseCount(mFilterDetailInfoList?.size ?: 0)
        adapter.listData = mFilterDetailInfoList
        adapter.notifyDataSetChanged()
        //
        Toast.makeText(activity, "共查找出${condition}案件${mFilterDetailInfoList?.size
                ?: 0}件", Toast.LENGTH_LONG).show()
    }

    companion object {
        private lateinit var mParentFragment: DangerFragment
        fun newInstance(fragment: DangerFragment): DangerRushCaseFragment {
            mParentFragment = fragment
            return DangerRushCaseFragment()
        }
    }
}

