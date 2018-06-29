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
        //叉掉一个筛选项
        val delViews = arrayListOf(first_del_condition, second_del_condition, third_del_condition, fourth_del_condition)
        delViews.forEach { it1 ->
            it1.setOnClickListener { it2 ->
                it2.visibility = View.GONE
                val map = mHashCondition.filterValues { it != it1.text.toString().replace("X", "").trim() }
                startSearchConditionText(map)
            }
        }
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

    //
    private lateinit var mHashCondition: Map<String, String>

    @SuppressLint("SetTextI18n")
    fun startSearchConditionText(condition: Map<String, String>) {
        this.mHashCondition = condition
        //
        save_current_del_condition.visibility = View.VISIBLE
        first_del_condition.visibility = View.GONE
        second_del_condition.visibility = View.GONE
        third_del_condition.visibility = View.GONE
        fourth_del_condition.visibility = View.GONE
        //
        if (condition.containsKey("rank") && condition.containsKey("type") && condition.containsKey("office")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskRank?.contains(condition["rank"].toString())!! && it.taskType?.contains(condition["type"].toString())!! && it.taskOffice?.contains(condition["office"].toString())!! }.toMutableList()
            //
            first_del_condition.text = condition["rank"] + "   X"
            second_del_condition.text = condition["type"] + "   X"
            third_del_condition.text = condition["office"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
            third_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("rank") && condition.containsKey("type")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskRank?.contains(condition["rank"].toString())!! && it.taskType?.contains(condition["type"].toString())!! }.toMutableList()
            //
            first_del_condition.text = condition["rank"] + "   X"
            second_del_condition.text = condition["type"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("type") && condition.containsKey("office")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskType?.contains(condition["type"].toString())!! && it.taskOffice?.contains(condition["office"].toString())!! }.toMutableList()
            //
            first_del_condition.text = condition["office"] + "   X"
            second_del_condition.text = condition["type"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("office") && condition.containsKey("rank")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskOffice?.contains(condition["office"].toString())!! && it.taskRank?.contains(condition["rank"].toString())!! }.toMutableList()
            //
            first_del_condition.text = condition["office"] + "   X"
            second_del_condition.text = condition["rank"] + "   X"
            first_del_condition.visibility = View.VISIBLE
            second_del_condition.visibility = View.VISIBLE
        } else if (condition.containsKey("rank")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskRank?.contains(condition["rank"].toString())!! }.toMutableList()
            //
            first_del_condition.visibility = View.VISIBLE
            first_del_condition.text = condition["rank"] + "   X"
        } else if (condition.containsKey("type")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskType?.contains(condition["type"].toString())!! }.toMutableList()
            //
            first_del_condition.visibility = View.VISIBLE
            first_del_condition.text = condition["type"] + "   X"
        } else if (condition.containsKey("office")) {
            mFilterDetailInfoList = mDetailInfoList.filter { it.taskOffice?.contains(condition["office"].toString())!! }.toMutableList()
            //
            first_del_condition.visibility = View.VISIBLE
            first_del_condition.text = condition["office"] + "   X"
        } else {
            mFilterDetailInfoList = mDetailInfoList
            save_current_del_condition.visibility = View.GONE
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
