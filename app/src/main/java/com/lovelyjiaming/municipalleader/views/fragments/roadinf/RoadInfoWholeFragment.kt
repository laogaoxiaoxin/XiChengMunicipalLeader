package com.lovelyjiaming.municipalleader.views.fragments.roadinf


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
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS
import com.lovelyjiaming.municipalleader.views.adapter.RoadInfoAdapter
import com.lovelyjiaming.municipalleader.views.adapter.RoadListJson
import kotlinx.android.synthetic.main.fragment_road_info_whole.*

class RoadInfoWholeFragment : Fragment() {
    private val mAdapter: RoadInfoAdapter by lazy {
        RoadInfoAdapter(activity as Context)
    }

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
            mAdapter.setData(result.roadListTask)
        })
        recyclerview_road_whole_info.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerview_road_whole_info.adapter = mAdapter
    }

    companion object {
        fun newInstance() =
                RoadInfoWholeFragment()
    }
}
