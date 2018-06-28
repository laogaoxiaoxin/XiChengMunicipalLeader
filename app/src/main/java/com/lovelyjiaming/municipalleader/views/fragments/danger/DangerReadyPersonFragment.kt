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
import com.lovelyjiaming.municipalleader.R.id.*
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.DangerReadyPersonAdapter
import kotlinx.android.synthetic.main.fragment_danger_ready_person.*

//http://39.104.80.111:8888/RoadLeader/EmergencyServlet?method=getEmergencyWorker
data class EmergencyWorkerClass(val EmergencyWorker: MutableList<EmergencyWorkerItem>)

data class EmergencyWorkerItem(val name: String, val age: String, val phone: String?, val team: String?, val remarks: String?)

class DangerReadyPersonFragment : Fragment() {

    val adapter: DangerReadyPersonAdapter by lazy {
        DangerReadyPersonAdapter(activity as Context)
    }
    private lateinit var mWorkList: MutableList<EmergencyWorkerItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_DANGER_ADDRESS + "getEmergencyWorker", {
            val result = Gson().fromJson(it, EmergencyWorkerClass::class.java)
            mWorkList = result.EmergencyWorker
            adapter.setData(mWorkList.groupBy { it.team }["north"]?.sortedByDescending { it.remarks })
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_danger_ready_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        danger_ready_person_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        danger_ready_person_recyclerview.adapter = adapter
        danger_ready_person_north.setTextColor(Color.parseColor("#DB394A"))

        //
        danger_ready_person_north.setOnClickListener {
            danger_ready_person_north.setTextColor(Color.parseColor("#DB394A"))
            danger_ready_person_south.setTextColor(Color.parseColor("#a9a9a9"))
            danger_ready_person_longkexing.setTextColor(Color.parseColor("#a9a9a9"))
            adapter.setData(mWorkList.groupBy { it.team }["north"]?.sortedByDescending { it.remarks })
        }
        danger_ready_person_south.setOnClickListener {
            danger_ready_person_south.setTextColor(Color.parseColor("#DB394A"))
            danger_ready_person_north.setTextColor(Color.parseColor("#a9a9a9"))
            danger_ready_person_longkexing.setTextColor(Color.parseColor("#a9a9a9"))
            adapter.setData(mWorkList.groupBy { it.team }["south"]?.sortedByDescending { it.remarks })
        }
        danger_ready_person_longkexing.setOnClickListener {
            danger_ready_person_longkexing.setTextColor(Color.parseColor("#DB394A"))
            danger_ready_person_south.setTextColor(Color.parseColor("#a9a9a9"))
            danger_ready_person_north.setTextColor(Color.parseColor("#a9a9a9"))
            adapter.setData(mWorkList.groupBy { it.team }["longkexing"]?.sortedByDescending { it.remarks })
        }
    }

    companion object {
        fun newInstance() = DangerReadyPersonFragment()
    }
}
