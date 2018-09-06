package com.lovelyjiaming.municipalleader.views.fragments.save


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.CureAppliedTaskClass
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.common_search_layout.*
import kotlinx.android.synthetic.main.common_top_del_condition.*
import kotlinx.android.synthetic.main.fragment_save_finish_task.*

class SaveFinishTaskFragment : Fragment() {
    private val mAdapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)
    }
    private var mbFirstTime = true
    private val mPresenter: CureFinishTaskPresenter = CureFinishTaskPresenter(this)
    //返回列表
    private var mListResult: List<InspectUndoneItemClass>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_finish_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        save_finish_swiperefresh.isRefreshing = true
        save_finish_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        save_finish_recyclerview.adapter = mAdapter
        //
        save_finish_swiperefresh.setOnRefreshListener {
            mPresenter.requestData()
            save_finish_del_condition.visibility = View.GONE
        }
        //
        edt_search_condition.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val list = mListResult?.filter {
                    it.taskName?.contains(s.toString()) ?: false
                }?.toMutableList()
                mAdapter.setData(list)
                mParentFragment.displayFinishedTaskCount(list?.size ?: 0)
                save_finish_del_condition.visibility = View.GONE//与筛选项互斥
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint && mbFirstTime) {
            mPresenter.requestData()
            mbFirstTime = false
        }
    }

    //第一次从网络来的数据
    fun setNetResultData(strResult: String) {
        save_finish_swiperefresh.isRefreshing = false
        mListResult = Gson().fromJson(strResult, CureAppliedTaskClass::class.java).CureAppliedList
        mAdapter.setData(mListResult?.toMutableList())
        mAdapter.holderType = "saveonlinetask"
        mParentFragment.displayFinishedTaskCount(mListResult?.size ?: 0)
    }

    fun startSearchConditionText(searchParam: MutableMap<String, String>) {
        var listTmp: List<InspectUndoneItemClass>? = null
        save_finish_del_condition.visibility = View.GONE
        first_del_condition.visibility = View.GONE
        third_del_condition.visibility = View.GONE
        second_del_condition.visibility = View.GONE
        edt_search_condition.setText("")

        if (searchParam.containsKey("rank")) {
            listTmp = mListResult?.filter {
                it.taskRank?.contains(searchParam["rank"].toString()) ?: false
            }
            first_del_condition.text = searchParam["rank"]
            first_del_condition.visibility = View.VISIBLE
            save_finish_del_condition.visibility = View.VISIBLE
        }
        if (searchParam.containsKey("type")) {
            listTmp = listTmp?.filter { it.taskType == searchParam["type"] } ?: mListResult?.filter { it.taskType == searchParam["type"] }
            third_del_condition.text = searchParam["type"]
            third_del_condition.visibility = View.VISIBLE
            save_finish_del_condition.visibility = View.VISIBLE
        }
        if (searchParam.containsKey("office")) {
            listTmp = listTmp?.filter { it.taskOffice == searchParam["office"] } ?: mListResult?.filter { it.taskOffice == searchParam["office"] }
            second_del_condition.text = searchParam["office"]
            second_del_condition.visibility = View.VISIBLE
            save_finish_del_condition.visibility = View.VISIBLE
        }
        mAdapter.setData(listTmp?.toMutableList())
        mParentFragment.displayFinishedTaskCount(listTmp?.size ?: 0)
    }

    companion object {
        lateinit var mParentFragment: SaveFragment
        fun newInstance(fragment: SaveFragment): SaveFinishTaskFragment {
            mParentFragment = fragment
            return SaveFinishTaskFragment()
        }
    }
}
