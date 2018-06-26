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
                if (mFilterDetailInfoList != null && mFilterDetailInfoList!!.size > 0) {
                    mFilterDetailInfoList = mFilterDetailInfoList?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                } else
                    mFilterDetailInfoList = mDetailInfoList.filter { it.taskName?.contains(p0.toString())!! }.toMutableList()
                //
                adapter.setData(mFilterDetailInfoList)
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

    //各种条件筛选后的结果集
    private var mFilterDetailInfoList: MutableList<InspectUndoneItemClass>? = null

    fun startSearchConditionText(condition: String) {
        when (condition) {
            "名称查询" -> {
                edt_search_condition.hint = "请输入正确的标题"
                edt_search_condition.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        if (mFilterDetailInfoList != null && mFilterDetailInfoList!!.size > 0) {
                            mFilterDetailInfoList = mFilterDetailInfoList?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                        } else
                            mFilterDetailInfoList = mDetailInfoList.filter { it.taskName?.contains(p0.toString())!! }.toMutableList()
                        //
                        adapter.setData(mFilterDetailInfoList)
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                })
            }
            "编号查询" -> {
                edt_search_condition.hint = "请输入正确的编号"
                edt_search_condition.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {
                        if (mFilterDetailInfoList != null && mFilterDetailInfoList!!.size > 0) {
                            mFilterDetailInfoList = mFilterDetailInfoList?.filter { it.taskNumber?.contains(p0.toString())!! }?.toMutableList()
                        } else
                            mFilterDetailInfoList = mDetailInfoList.filter { it.taskNumber?.contains(p0.toString())!! }.toMutableList()
                        //
                        adapter.setData(mFilterDetailInfoList)
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                })
            }
            "日期查询" -> {
            }
            "道路破损" -> {
                mFilterDetailInfoList = mDetailInfoList.filter { it.taskType == "道路破损" }.toMutableList()
                mParentFragment.displayCountText(mFilterDetailInfoList?.size ?: 0)
                adapter.setData(mFilterDetailInfoList)
            }
            "步道破损" -> {
                mFilterDetailInfoList = mDetailInfoList.filter { it.taskType == "步道破损" }.toMutableList()
                mParentFragment.displayCountText(mFilterDetailInfoList?.size ?: 0)
                adapter.setData(mFilterDetailInfoList)
            }
            "附属设施破损" -> {
                mFilterDetailInfoList = mDetailInfoList.filter { it.taskType == "附属设施破损" }.toMutableList()
                mParentFragment.displayCountText(mFilterDetailInfoList?.size ?: 0)
                adapter.setData(mFilterDetailInfoList)
            }
        }
    }

    companion object {
        private lateinit var mParentFragment: SaveFragment
        fun newInstance(fragment: SaveFragment): SaveCurrentTaskFragment {
            mParentFragment = fragment
            return SaveCurrentTaskFragment()
        }
    }
}
