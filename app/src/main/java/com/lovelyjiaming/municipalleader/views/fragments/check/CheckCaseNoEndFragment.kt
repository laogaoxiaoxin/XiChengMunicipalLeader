package com.lovelyjiaming.municipalleader.views.fragments.check

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
import kotlinx.android.synthetic.main.fragment_check_case_no_end.*

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

    fun startSearch(condition: String) {
        when (condition) {
            "一级养护", "二级养护", "三级养护" ->
                mFilterDetailInfo = mListDetailInfo?.filter {
                    it.taskRank?.contains(condition) ?: false
                }?.toMutableList()
            "私掘私占", "停车场巡查", "公租自行车", "路侧停车", "公共服务设施" ->
                mFilterDetailInfo = mListDetailInfo?.filter { it.taskType?.contains(condition)!! }?.toMutableList()
            "审批掘路" ->
                mFilterDetailInfo = mListDetailInfo?.filter { it.taskType == "电力" || it.taskType == "电信" || it.taskType == "降水井" || it.taskType == "雨污水" || it.taskType == "路灯" || it.taskType == "热力" }?.toMutableList()
            else -> {
                mFilterDetailInfo = mListDetailInfo?.filter { it.taskOffice == condition }?.toMutableList()
            }
        }
        mParentFragment.displayCaseCount(mFilterDetailInfo?.size ?: 0)
        adapter.setData(mFilterDetailInfo)
        Toast.makeText(activity, "共查找出${condition}案件${mFilterDetailInfo?.size
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
