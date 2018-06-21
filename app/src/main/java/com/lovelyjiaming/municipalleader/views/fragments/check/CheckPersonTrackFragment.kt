package com.lovelyjiaming.municipalleader.views.fragments.check

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.route.*
import com.baidu.mapapi.utils.CoordinateConverter
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.InspectLocationClass
import com.lovelyjiaming.municipalleader.utils.InspectLocationItemClass
import com.lovelyjiaming.municipalleader.utils.WalkingRouteOverlay
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.activitys.PersonListTrackActivity
import kotlinx.android.synthetic.main.fragment_check_person_track.*


data class InspectTrackItem(val x: String?, val y: String?, val time: String?)
data class InspectTrack(val InspectTrack: MutableList<InspectTrackItem>)

class CheckPersonTrackFragment : Fragment() {

    private lateinit var mWorkTypeMap: Map<String, List<InspectLocationItemClass>>
    //
    private lateinit var mSearch: RoutePlanSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_person_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        check_person_track_mapview.onCreate(activity, savedInstanceState)
        //mapUI settings
        check_person_track_mapview.map.uiSettings.isRotateGesturesEnabled = false
        check_person_track_mapview.map.uiSettings.isOverlookingGesturesEnabled = false
        check_person_track_mapview.map.uiSettings.isCompassEnabled = false
        mSearch = RoutePlanSearch.newInstance()
        //先请求分组信息，再获取一次人员位置
        XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getLocation", {
            mWorkTypeMap = Gson().fromJson(it, InspectLocationClass::class.java).InspectLocation.groupBy { it.WorkType }
        }, null)
        //
        click_jiakongxian.setOnClickListener {
            val intent = Intent(activity, PersonListTrackActivity::class.java)
            intent.putExtra("groupname", "架空线")
            intent.putExtra("personlist", mWorkTypeMap["架空线"]?.toTypedArray())
            startActivityForResult(intent, 991)
        }
        click_daoluxuncha.setOnClickListener {
            val intent = Intent(activity, PersonListTrackActivity::class.java)
            intent.putExtra("groupname", "道路巡查")
            intent.putExtra("personlist", mWorkTypeMap["道路巡查"]?.toTypedArray())
            startActivityForResult(intent, 992)
        }
        click_shenpijuelu.setOnClickListener {
            val intent = Intent(activity, PersonListTrackActivity::class.java)
            intent.putExtra("groupname", "审批掘路")
            intent.putExtra("personlist", mWorkTypeMap["审批掘路"]?.toTypedArray())
            startActivityForResult(intent, 993)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != 0 && resultCode == 1077 && data != null) {
            val name = data.getStringExtra("name")
            XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getTrack", {
                convertLaLoAddress(Gson().fromJson(it, InspectTrack::class.java).InspectTrack)
            }, hashMapOf("username" to name))
        }
    }

    private var listReadyDraw: MutableList<LatLng>? = mutableListOf()

    private fun convertLaLoAddress(listAddress: MutableList<InspectTrackItem>?) {
        if (listAddress == null || listAddress.size == 0) {
            Toast.makeText(activity, "此员工暂无轨迹信息", Toast.LENGTH_SHORT).show()
            return
        }

        check_person_track_mapview.map.clear()

        listAddress.let {
            listReadyDraw?.clear()
            listAddress.forEach {
                if (it.x != "0.0" && it.y != "0.0") {
                    val converter = CoordinateConverter()
                    converter.from(CoordinateConverter.CoordType.COMMON)
                    converter.coord(LatLng(it.x?.toDouble()!!, it.y?.toDouble()!!))
                    listReadyDraw?.add(converter.convert())
                }
            }

            mSearch.setOnGetRoutePlanResultListener(object : OnGetRoutePlanResultListener {
                override fun onGetIndoorRouteResult(p0: IndoorRouteResult?) {
                }

                override fun onGetTransitRouteResult(p0: TransitRouteResult?) {
                }

                override fun onGetDrivingRouteResult(p0: DrivingRouteResult?) {
                }

                override fun onGetWalkingRouteResult(p0: WalkingRouteResult?) {
                    val overlay = WalkingRouteOverlay(check_person_track_mapview.map)
                    overlay.setData(p0?.routeLines?.get(0))
                    overlay.addToMap()
                }

                override fun onGetMassTransitRouteResult(p0: MassTransitRouteResult?) {
                }

                override fun onGetBikingRouteResult(p0: BikingRouteResult?) {
                }

            })
            this.startPlanThread()
        }
    }

    private fun startPlanThread() {
        val option = WalkingRoutePlanOption()
        check_person_track_mapview.map.setMapStatus(MapStatusUpdateFactory
                .newLatLngZoom(listReadyDraw!![0], 16.5f))
        //开始正式规划路线
        Thread {
            val stepSize = if (listReadyDraw?.size!! >= 300) 48 else 10
            //
            for (i in 0 until listReadyDraw?.size!! step stepSize) {
                if (i >= listReadyDraw?.size!! - 1 || (i + stepSize) >= listReadyDraw?.size!! - 1) break
                val nodeStart = listReadyDraw?.get(i)
                val nodeEnd = listReadyDraw?.get(i + stepSize)
                option.from(PlanNode.withLocation(nodeStart))
                option.to(PlanNode.withLocation(nodeEnd))
                mSearch.walkingSearch(option)
                Thread.sleep(300)
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()
        check_person_track_mapview.onResume()
    }

    override fun onPause() {
        super.onPause()
        check_person_track_mapview.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        check_person_track_mapview.onDestroy()
        mSearch.destroy()
    }


    companion object {
        fun newInstance() = CheckPersonTrackFragment()
    }


}
