package com.lovelyjiaming.municipalleader.views.fragments.check

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import kotlinx.android.synthetic.main.fragment_check_person_track.*


data class InspectTrackItem(val x: String?, val y: String?, val time: String?)
data class InspectTrack(val InspectTrack: MutableList<InspectTrackItem>)

class CheckPersonTrackFragment : Fragment(), AdapterView.OnItemClickListener {

    private lateinit var mWorkTypeMap: Map<String, List<InspectLocationItemClass>>
    private val search = RoutePlanSearch.newInstance()

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
        //
        listview_person_track_filter.adapter = ArrayAdapter(this.activity, android.R.layout.simple_list_item_1, arrayOf("架空线", "道路巡查", "审批掘路"))
        listview_person_track_filter.onItemClickListener = this
        //先请求分组信息，再获取一次人员位置
        XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getLocation", {
            mWorkTypeMap = Gson().fromJson(it, InspectLocationClass::class.java).InspectLocation.groupBy { it.WorkType }
        }, null)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when ((p1 as? TextView)?.text.toString()) {
            "架空线" -> {
                listview_person_track_filter.adapter = ArrayAdapter(this.activity, android.R.layout.simple_list_item_1, mWorkTypeMap["架空线"]?.map { it.username })
                listview_person_track_filter.layoutParams.height = 500
            }
            "道路巡查" -> {
                listview_person_track_filter.adapter = ArrayAdapter(this.activity, android.R.layout.simple_list_item_1, mWorkTypeMap["道路巡查"]?.map { it.username })
                listview_person_track_filter.layoutParams.height = 500
            }
            "审批掘路" -> {
                listview_person_track_filter.adapter = ArrayAdapter(this.activity, android.R.layout.simple_list_item_1, mWorkTypeMap["审批掘路"]?.map { it.username })
                listview_person_track_filter.layoutParams.height = 500
            }
            else -> {
                check_person_track_mapview.map.clear()
                listview_person_track_filter.layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT
                listview_person_track_filter.adapter = ArrayAdapter(this.activity, android.R.layout.simple_list_item_1, arrayOf("架空线", "道路巡查", "审批掘路"))
                XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getTrack", {
                    convertLaLoAddress(Gson().fromJson(it, InspectTrack::class.java).InspectTrack)
                }, hashMapOf("username" to (p1 as? TextView)?.text.toString()))
            }
        }
    }

    private var listReadyDraw: MutableList<LatLng>? = mutableListOf()

    private fun convertLaLoAddress(listAddress: MutableList<InspectTrackItem>?) {
        if (listAddress == null || listAddress.size == 0) {
            Toast.makeText(activity, "此员工暂无轨迹信息", Toast.LENGTH_SHORT).show()
            return
        }

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

            search.setOnGetRoutePlanResultListener(object : OnGetRoutePlanResultListener {
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

            val option = WalkingRoutePlanOption()
            check_person_track_mapview.map.setMapStatus(MapStatusUpdateFactory
                    .newLatLngZoom(listReadyDraw!![0], 16.5f))
            //开始正式规划路线
            Thread {
                var stepSize = 0
                if (listReadyDraw?.size!! >= 300) stepSize = 60 else stepSize = 10
                //
                for (i in 0 until listReadyDraw?.size!! step stepSize) {
                    if (i >= listReadyDraw?.size!! - 1 || (i + stepSize) >= listReadyDraw?.size!! - 1) break
                    val nodeStart = listReadyDraw?.get(i)
                    val nodeEnd = listReadyDraw?.get(i + stepSize)
                    option.from(PlanNode.withLocation(nodeStart))
                    option.to(PlanNode.withLocation(nodeEnd))
                    search.walkingSearch(option)
                    Thread.sleep(50)
                }
            }.start()

        }
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
    }


    companion object {
        fun newInstance() = CheckPersonTrackFragment()
    }


}
