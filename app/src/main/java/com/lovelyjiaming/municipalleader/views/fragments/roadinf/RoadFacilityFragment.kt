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
import com.lovelyjiaming.municipalleader.views.adapter.RoadFacilityAdapter
import com.lovelyjiaming.municipalleader.views.adapter.RoadFacilityJson
import kotlinx.android.synthetic.main.fragment_road_facility.*

class RoadFacilityFragment : Fragment() {
    //
    private val mAdapter: RoadFacilityAdapter by lazy {
        RoadFacilityAdapter(activity as Context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_road_facility, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AutoUtils.auto(view)
        recyclerview_road_facility.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerview_road_facility.adapter = mAdapter
        XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getFacilitiesList", {
            val result = Gson().fromJson(it, RoadFacilityJson::class.java)
            mAdapter.setData(result.facilitiesListTask)
        })
    }


    companion object {
        fun newInstance() = RoadFacilityFragment()
    }
}
