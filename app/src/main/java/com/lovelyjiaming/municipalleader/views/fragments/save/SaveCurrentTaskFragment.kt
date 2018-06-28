package com.lovelyjiaming.municipalleader.views.fragments.save

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
import kotlinx.android.synthetic.main.fragment_save_current_task.*

class SaveCurrentTaskFragment : Fragment() {

    val adapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)//复用巡查未结案item
    }
    private lateinit var mDetailInfoList: MutableList<InspectUndoneItemClass>
    //各种条件筛选后的结果集
    private var mFilterDetailInfoList: MutableList<InspectUndoneItemClass>? = null

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
                //如果已经筛选过一次了，比如按照tasktype筛过一次了
                val list = if (mFilterDetailInfoList != null && mFilterDetailInfoList!!.size > 0) {
                    mFilterDetailInfoList?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                } else
                    mDetailInfoList.filter { it.taskName?.contains(p0.toString())!! }.toMutableList()
                //
                mParentFragment.displayCountText(list?.size ?: 0)
                adapter.setData(list)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun requestData() {
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getOnLineTask", {
            val result = Gson().fromJson(it, CureOnLineTaskClass::class.java)
            mDetailInfoList = result.CureOnLineTask.toMutableList()
            adapter.holderType = "saveonlinetask"
            adapter.setData(mDetailInfoList)//复用
            mParentFragment.displayCountText(mDetailInfoList.size)
            save_current_swiperefresh.isRefreshing = false
        })
    }

    fun startSearchConditionText(condition: HashMap<String, String>) {
        if (condition.containsKey("rank") && condition.containsKey("type") && condition.containsKey("office")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskRank?.contains(condition["rank"].toString())!! && it.taskType?.contains(condition["type"].toString())!! && it.taskOffice?.contains(condition["office"].toString())!! }.toMutableList()
        } else if (condition.containsKey("rank") && condition.containsKey("type")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskRank?.contains(condition["rank"].toString())!! && it.taskType?.contains(condition["type"].toString())!! }.toMutableList()
        } else if (condition.containsKey("type") && condition.containsKey("office")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskType?.contains(condition["type"].toString())!! && it.taskOffice?.contains(condition["office"].toString())!! }.toMutableList()
        } else if (condition.containsKey("office") && condition.containsKey("rank")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskOffice?.contains(condition["office"].toString())!! && it.taskRank?.contains(condition["rank"].toString())!! }.toMutableList()
        } else if (condition.containsKey("rank")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskRank?.contains(condition["rank"].toString())!! }.toMutableList()
        } else if (condition.containsKey("type")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskType?.contains(condition["type"].toString())!! }.toMutableList()
        } else if (condition.containsKey("office")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskOffice?.contains(condition["office"].toString())!! }.toMutableList()
        }
        //
        mParentFragment.displayCountText(mFilterDetailInfoList?.size ?: 0)
        adapter.setData(mFilterDetailInfoList)
        Toast.makeText(activity, "共查找出案件${mFilterDetailInfoList?.size
                ?: 0}件", Toast.LENGTH_LONG).show()
    }

    companion object {
        private lateinit var mParentFragment: SaveFragment
        fun newInstance(fragment: SaveFragment): SaveCurrentTaskFragment {
            mParentFragment = fragment
            return SaveCurrentTaskFragment()
        }
    }
}
