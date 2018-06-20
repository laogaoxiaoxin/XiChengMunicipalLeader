package com.lovelyjiaming.municipalleader.views.fragments.danger


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import com.lovelyjiaming.municipalleader.utils.EmergencyOndutyMemberClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.DangerReadyPersonAdapter
import kotlinx.android.synthetic.main.fragment_danger_ready_person.*

class DangerReadyPersonFragment : Fragment() {

    val adapter: DangerReadyPersonAdapter by lazy {
        DangerReadyPersonAdapter(activity as Context)
    }
    var strChangAnStreet: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_danger_ready_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        danger_ready_person_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        danger_ready_person_recyclerview.adapter = adapter
        //
        danger_ready_person_date.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context, {
                danger_ready_person_date.text = "查询时间：" + it
                requestData()
            })

        }
        danger_ready_person_north.setOnClickListener {
            danger_ready_person_north.setTextColor(Color.parseColor("#DB394A"))
            danger_ready_person_south.setTextColor(Color.parseColor("#a9a9a9"))
            strChangAnStreet = "长安街以北"
            requestData()
        }
        danger_ready_person_south.setOnClickListener {
            danger_ready_person_south.setTextColor(Color.parseColor("#DB394A"))
            danger_ready_person_north.setTextColor(Color.parseColor("#a9a9a9"))
            strChangAnStreet = "长安街以南"
            requestData()
        }
    }

    private fun requestData() {
        if (strChangAnStreet.isNullOrEmpty()) return
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_DANGER_ADDRESS + "getOndutyMember", {
            val result = Gson().fromJson(it, EmergencyOndutyMemberClass::class.java)
            adapter.listData = result.EmergencyOndutyMember
            adapter.notifyDataSetChanged()
        }, hashMapOf("dutyDate" to danger_ready_person_date.text.toString().replace("查询时间：", ""), "dutyArea" to strChangAnStreet!!))
    }

    companion object {
        fun newInstance() = DangerReadyPersonFragment()
    }
}
