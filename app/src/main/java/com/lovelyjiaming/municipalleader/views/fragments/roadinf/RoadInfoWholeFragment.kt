package com.lovelyjiaming.municipalleader.views.fragments.roadinf


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
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS
import com.lovelyjiaming.municipalleader.views.adapter.RoadInfoAdapter
import com.lovelyjiaming.municipalleader.views.adapter.RoadListJson
import com.lovelyjiaming.municipalleader.views.adapter.RoadListTaskItem
import kotlinx.android.synthetic.main.fragment_road_info_whole.*

class RoadInfoWholeFragment : Fragment() {
    private val mAdapter: RoadInfoAdapter by lazy {
        RoadInfoAdapter(activity as Context)
    }
    private var mListData: MutableList<RoadListTaskItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_road_info_whole, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AutoUtils.auto(view)
        XCNetWorkUtil.invokeGetRequest(activity, NETWORK_BASIC_SAVE_ADDRESS + "getRoadList", {
            val result = Gson().fromJson(it, RoadListJson::class.java)
            mListData = result.roadListTask
            mListData?.let { it1 ->
                mAdapter.setData(it1)
            }
        })
        recyclerview_road_whole_info.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerview_road_whole_info.adapter = mAdapter
        //
        edt_road_info_whole.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val list = mListData?.filter { it.taskName?.contains(p0.toString()) ?: false || it.startName?.contains(p0.toString()) ?: false || it.endName?.contains(p0.toString()) ?: false }
                list?.let {
                    mAdapter.setData(list.toMutableList())
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    companion object {
        fun newInstance() =
                RoadInfoWholeFragment()
    }
}
