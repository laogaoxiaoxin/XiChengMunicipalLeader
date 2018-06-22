package com.lovelyjiaming.municipalleader.views.fragments.check


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.InspectUndoneClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.fragment_check_case_no_end.*

class CheckCaseNoEndFragment : Fragment() {

    val adapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)
    }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        XCNetWorkUtil.cancelRequest()
    }

    private fun requestData() {
        //network
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getUndone", {
            val result = Gson().fromJson(it, InspectUndoneClass::class.java)
            val listResult = result.InspectUndone
            adapter.setData(listResult.toMutableList())
            check_noend_swiperefresh?.let {
                it.isRefreshing = false
            }
        })
    }

    companion object {
        fun newInstance() = CheckCaseNoEndFragment()
    }
}
