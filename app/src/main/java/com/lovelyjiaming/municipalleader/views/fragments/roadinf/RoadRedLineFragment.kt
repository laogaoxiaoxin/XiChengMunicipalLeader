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
import com.lovelyjiaming.municipalleader.views.adapter.RoadRedLineAdapter
import com.lovelyjiaming.municipalleader.views.adapter.RoadRedLineItem
import com.lovelyjiaming.municipalleader.views.adapter.RoadRedLineJson
import kotlinx.android.synthetic.main.fragment_road_red_line.*

class RoadRedLineFragment : Fragment() {
    private val mAdapter: RoadRedLineAdapter by lazy {
        RoadRedLineAdapter(activity as Context)
    }
    private var mListInfo: MutableList<RoadRedLineItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_road_red_line, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AutoUtils.auto(view)
        recyclerview_road_redline.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerview_road_redline.adapter = mAdapter
        //
        XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getRedLineList", {
            val result = Gson().fromJson(it, RoadRedLineJson::class.java)
            mListInfo = result.roadRedLineTask
            mListInfo?.let { it1 ->
                mAdapter.setData(it1)
            }
        })
        //
        edt_road_red_line.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val list = mListInfo?.filter { it.taskName?.contains(p0.toString()) ?: false || it.taskStreet?.contains(p0.toString()) ?: false }
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
                RoadRedLineFragment()
    }
}
