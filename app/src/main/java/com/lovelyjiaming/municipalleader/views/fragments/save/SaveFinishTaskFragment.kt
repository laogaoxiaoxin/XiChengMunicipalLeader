package com.lovelyjiaming.municipalleader.views.fragments.save


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.CureAppliedTaskClass
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.fragment_save_finish_task.*

class SaveFinishTaskFragment : Fragment() {
    private val mAdapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)
    }
    private var mbFirstTime = true
    private val mPresenter: CureFinishTaskPresenter = CureFinishTaskPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint && mbFirstTime) {
            mPresenter.requestData()
            mbFirstTime = false
        }
    }

    //返回列表
    private var mListResult: List<InspectUndoneItemClass>? = null

    //第一次从网络来的数据
    fun setNetResultData(strResult: String) {
        save_finish_swiperefresh.isRefreshing = false
        mListResult = Gson().fromJson(strResult, CureAppliedTaskClass::class.java).CureAppliedList
        mAdapter.setData(mListResult?.toMutableList())
        mParentFragment.displayFinishedTaskCount(mListResult?.size ?: 0)
    }

    fun startSearchConditionText(searchParam: MutableMap<String, String>) {
        var listTmp: List<InspectUndoneItemClass>? = null
        if (searchParam.containsKey("rank")) {
            listTmp = mListResult?.filter {
                it.taskRank?.contains(searchParam["rank"].toString()) ?: false
            }
        }
        if (searchParam.containsKey("type")) {
            listTmp = listTmp?.filter { it.taskType == searchParam["type"] }
        }
        if (searchParam.containsKey("office")) {
            listTmp = listTmp?.filter { it.taskOffice == searchParam["office"] }
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
