package com.lovelyjiaming.municipalleader.views.fragments.save

import android.annotation.SuppressLint
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
import com.lovelyjiaming.municipalleader.utils.CureOnLineTaskClass
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.common_search_layout.*
import kotlinx.android.synthetic.main.common_top_del_condition.*
import kotlinx.android.synthetic.main.fragment_save_current_task.*
import java.text.SimpleDateFormat

class SaveCurrentTaskFragment : Fragment() {

    val adapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)//复用巡查未结案item
    }
    private var mDetailInfoList: MutableList<InspectUndoneItemClass>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_save_current_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_current_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        save_current_recyclerview.adapter = adapter
        save_current_swiperefresh.isRefreshing = true
        requestData()
        //
        save_current_swiperefresh.setOnRefreshListener {
            requestData()
        }
        //默认按照名称查询
        edt_search_condition.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                save_current_del_condition.visibility = View.GONE
                //如果已经筛选过一次了，比如按照tasktype筛过一次了
                val list = mDetailInfoList?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                mParentFragment.displayOnLineCountText(list?.size ?: 0)
                adapter.setData(list)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //叉掉一个筛选项
//        val delViews = arrayListOf(first_del_condition, second_del_condition, third_del_condition, fourth_del_condition)
//        delViews.forEach { it1 ->
//            it1.setOnClickListener { it2 ->
//                it2.visibility = View.GONE
//                if (it1.text.toString().startsWith("20")) {
//                    mMapCondition.remove("startdate")
//                    mMapCondition.remove("enddate")
//                    startSearchConditionText(mMapCondition)
//                } else {
//                    val map = mMapCondition.filterValues { it != it1.text.toString().replace("X", "").trim() }
//                    startSearchConditionText(map as MutableMap<String, String>)
//                }
//            }
//        }
    }

    private fun requestData() {
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getOnLineList", {
            val result = Gson().fromJson(it, CureOnLineTaskClass::class.java)
            mDetailInfoList = result.CureOnLineList.toMutableList()
            adapter.holderType = "saveonlinetask"
            adapter.setData(mDetailInfoList)//复用
            mParentFragment.displayOnLineCountText(mDetailInfoList?.size ?: 0)
            save_current_swiperefresh.isRefreshing = false
        })
    }

    //
    private lateinit var mMapCondition: MutableMap<String, String>

    @SuppressLint("SetTextI18n")
    fun startSearchConditionText(condition: MutableMap<String, String>) {
        this.mMapCondition = condition
        //缓存，保证成员变量mDetailInfoList总是完整的
        var localTmpDetailInfo = mDetailInfoList
        //
        first_del_condition.visibility = View.GONE
        second_del_condition.visibility = View.GONE
        third_del_condition.visibility = View.GONE
        fourth_del_condition.visibility = View.GONE

        //先过滤时间
        if (mMapCondition.containsKey("startdate") && mMapCondition.containsKey("enddate")) {
            fourth_del_condition.text = mMapCondition["startdate"] + "~" + mMapCondition["enddate"]
            save_current_del_condition.visibility = View.VISIBLE
            fourth_del_condition.visibility = View.VISIBLE
            //比对时间
            val spFormat = SimpleDateFormat("yyyy-MM-dd")
            val start = spFormat.parse(mMapCondition["startdate"])
            val end = spFormat.parse(mMapCondition["enddate"])
            localTmpDetailInfo = localTmpDetailInfo?.filter { start.time <= spFormat.parse(it.taskDate).time && spFormat.parse(it.taskDate).time <= end.time }?.toMutableList()
        }
        //type
        if (mMapCondition.containsKey("type")) {
            third_del_condition.text = mMapCondition["type"]
            third_del_condition.visibility = View.VISIBLE
            save_current_del_condition.visibility = View.VISIBLE
            localTmpDetailInfo = localTmpDetailInfo?.filter { it.taskType == mMapCondition["key"] }?.toMutableList()
        }
        //rank
        if (mMapCondition.containsKey("rank")) {
            first_del_condition.text = mMapCondition["rank"]
            first_del_condition.visibility = View.VISIBLE
            save_current_del_condition.visibility = View.VISIBLE
            localTmpDetailInfo = localTmpDetailInfo?.filter { it.taskRank == mMapCondition["rank"] }?.toMutableList()
        }
        //office
        if (mMapCondition.containsKey("office")) {
            second_del_condition.text = mMapCondition["office"]
            second_del_condition.visibility = View.VISIBLE
            save_current_del_condition.visibility = View.VISIBLE
            localTmpDetailInfo = localTmpDetailInfo?.filter { it.taskOffice == mMapCondition["office"] }?.toMutableList()
        }
        //
        mParentFragment.displayOnLineCountText(localTmpDetailInfo?.size ?: 0)
        adapter.setData(localTmpDetailInfo)
        Toast.makeText(activity, "共查找出案件${localTmpDetailInfo?.size
                ?: 0}件", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private lateinit var mParentFragment: SaveFragment
        fun newInstance(fragment: SaveFragment): SaveCurrentTaskFragment {
            mParentFragment = fragment
            return SaveCurrentTaskFragment()
        }
    }
}
