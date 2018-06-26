package com.lovelyjiaming.municipalleader.views.fragments.save


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.CureOnLineTaskClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.activitys.MainActivity
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.fragment_save_current_task.*

class SaveCurrentTaskFragment : Fragment() {

    val adapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)//复用巡查未结案item
    }

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
    }

    private fun requestData() {
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getOnLineTask", {
            val result = Gson().fromJson(it, CureOnLineTaskClass::class.java)
            adapter.holderType = "saveonlinetask"
            mParentFragment.displayCountText(result.CureOnLineTask.size)
            adapter.setData(result.CureOnLineTask.toMutableList())//复用
            save_current_swiperefresh.isRefreshing = false
        })
    }

    companion object {
        private lateinit var mParentFragment: SaveFragment
        fun newInstance(fragment: SaveFragment): SaveCurrentTaskFragment {
            mParentFragment = fragment
            return SaveCurrentTaskFragment()
        }
    }
}
