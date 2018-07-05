package com.lovelyjiaming.municipalleader.views.fragments.check

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
import com.lovelyjiaming.municipalleader.utils.InspectUndoneClass
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.common_search_layout.*
import kotlinx.android.synthetic.main.common_top_del_condition.*
import kotlinx.android.synthetic.main.fragment_check_case_no_end.*
import java.text.SimpleDateFormat

class CheckCaseNoEndFragment : Fragment() {

    val adapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)
    }
    //
    private var mListDetailInfo: MutableList<InspectUndoneItemClass>? = null
    private var mFilterDetailInfo: MutableList<InspectUndoneItemClass>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_case_no_end, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        check_noend_case_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        check_noend_case_recyclerview.adapter = adapter
        check_noend_swiperefresh.isRefreshing = true
        requestData()
        check_noend_swiperefresh.setOnRefreshListener {
            requestData()
        }
        //
        //默认按照名称查询
        edt_search_condition.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //如果已经筛选过一次了，比如按照tasktype筛过一次了
                val list = if (mFilterDetailInfo != null && mFilterDetailInfo!!.size > 0) {
                    mFilterDetailInfo?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                } else
                    mListDetailInfo?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                //
                mParentFragment.displayCaseCount(list?.size ?: 0)
                adapter.setData(list)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        //叉掉筛选项
        val arrayDelView = arrayListOf(first_del_condition, second_del_condition, third_del_condition, fourth_del_condition)
        arrayDelView.forEach { it1 ->
            it1.setOnClickListener {
                it1.visibility = View.GONE
                startSearch(mMapHashCondition.filterValues { it != it1.text.toString().replace("X", "").trim() } as MutableMap<String, String>)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        XCNetWorkUtil.cancelRequest()
    }

    private fun requestData() {
        //network
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getUndone", {
            val result = Gson().fromJson(it, InspectUndoneClass::class.java)
            mListDetailInfo = result.InspectUndone.toMutableList()
            adapter.setData(mListDetailInfo)
            mParentFragment.displayCaseCount(mListDetailInfo?.size ?: 0)
            adapter.holderType = "checknoend"//未结案件
            check_noend_swiperefresh?.let {
                it.isRefreshing = false
            }
        })
    }

    //
    private lateinit var mMapHashCondition: MutableMap<String, String>

    @SuppressLint("SetTextI18n")
    fun startSearch(condition: MutableMap<String, String>) {
        this.mMapHashCondition = condition
        //缓存，保证成员变量mDetailInfoList总是完整的
        var localTmpDetailInfo = mListDetailInfo
        //
        check_del_view_condition.visibility = View.VISIBLE
        first_del_condition.visibility = View.GONE
        second_del_condition.visibility = View.GONE
        third_del_condition.visibility = View.GONE
        fourth_del_condition.visibility = View.GONE
        //
        if (condition.containsKey("startdate") && condition.containsKey("enddate")) {
            //比对时间
            val spFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val start = spFormat.parse(condition["startdate"])
            val end = spFormat.parse(condition["enddate"])
            localTmpDetailInfo = localTmpDetailInfo?.filter { start.time <= spFormat.parse(it.taskDate).time && spFormat.parse(it.taskDate).time <= end.time }?.toMutableList()
            //
            fourth_del_condition.visibility = View.VISIBLE
            check_del_view_condition.visibility = View.VISIBLE
            fourth_del_condition.text = condition["startdate"] + "~" + condition["enddate"]
        }

        if (condition.containsKey("rank") && condition.containsKey("type") && condition.containsKey("office")) {
            mFilterDetailInfo = localTmpDetailInfo?.filter { it.taskRank?.contains(condition["rank"].toString()) ?: false && it.taskType?.contains(condition["type"].toString())!! && it.taskOffice?.contains(condition["office"].toString()) ?: false }?.toMutableList()
            //
            first_del_condition.text = condition["rank"] + "   X"
            second_del_condition.text = condition["type"] + "   X"
            third_del_condition.text = condition["office"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
            third_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("rank") && condition.containsKey("type")) {
            mFilterDetailInfo = localTmpDetailInfo?.filter { it.taskRank?.contains(condition["rank"].toString()) ?: false && it.taskType?.contains(condition["type"].toString())!! }?.toMutableList()
            //
            first_del_condition.text = condition["rank"] + "   X"
            second_del_condition.text = condition["type"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("type") && condition.containsKey("office")) {
            mFilterDetailInfo = localTmpDetailInfo?.filter { it.taskType?.contains(condition["type"].toString())!! && it.taskOffice?.contains(condition["office"].toString()) ?: false }?.toMutableList()
            //
            first_del_condition.text = condition["type"] + "   X"
            second_del_condition.text = condition["office"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("office") && condition.containsKey("rank")) {
            mFilterDetailInfo = localTmpDetailInfo?.filter { it.taskOffice?.contains(condition["office"].toString()) ?: false && it.taskRank?.contains(condition["rank"].toString()) ?: false }?.toMutableList()
            //
            first_del_condition.text = condition["rank"] + "   X"
            second_del_condition.text = condition["office"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("rank")) {
            mFilterDetailInfo = localTmpDetailInfo?.filter {
                it.taskRank?.contains(condition["rank"].toString()) ?: false
            }?.toMutableList()
            //
            first_del_condition.text = condition["rank"] + "   X"
            first_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("type")) {
            mFilterDetailInfo = if (condition["type"].toString() == "审批掘路")
                localTmpDetailInfo?.filter { it.taskType == "电力" || it.taskType == "电信" || it.taskType == "降水井" || it.taskType == "雨污水" || it.taskType == "路灯" || it.taskType == "热力" }?.toMutableList()
            else
                localTmpDetailInfo?.filter { it.taskType?.contains(condition["type"].toString())!! }?.toMutableList()
            //
            first_del_condition.text = condition["type"] + "   X"
            first_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("office")) {
            mFilterDetailInfo = localTmpDetailInfo?.filter {
                it.taskOffice?.contains(condition["office"].toString()) ?: false
            }?.toMutableList()
            //
            first_del_condition.text = condition["office"] + "   X"
            first_del_condition.visibility = View.VISIBLE
        } else {
            mFilterDetailInfo = localTmpDetailInfo
            check_del_view_condition.visibility = View.GONE
        }
        mParentFragment.displayCaseCount(mFilterDetailInfo?.size ?: 0)
        adapter.setData(mFilterDetailInfo)
        Toast.makeText(activity, "共查找出案件${mFilterDetailInfo?.size
                ?: 0}件", Toast.LENGTH_LONG).show()
    }

    companion object {
        private lateinit var mParentFragment: CheckFragment
        fun newInstance(fragment: CheckFragment): CheckCaseNoEndFragment {
            mParentFragment = fragment
            return CheckCaseNoEndFragment()
        }
    }
}
