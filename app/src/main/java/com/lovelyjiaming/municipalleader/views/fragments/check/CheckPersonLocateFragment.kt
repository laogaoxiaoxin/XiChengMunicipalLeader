package com.lovelyjiaming.municipalleader.views.fragments.check

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.model.LatLng

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.fragment_check_person_locate.*

class CheckPersonLocateFragment : Fragment() {
    //
    lateinit var mLatLngBlue: LatLng
    lateinit var mLatLngRed: LatLng
    lateinit var mLatLngYellow: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        check_person_locate_mapview.onResume()
    }

    override fun onPause() {
        super.onPause()
        check_person_locate_mapview.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        check_person_locate_mapview.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_check_person_locate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapView(savedInstanceState)
        //网络请求
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_ADDRESS + "getLocation", { Log.i("response==", it) })
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        check_person_locate_mapview.onCreate(activity, savedInstanceState)
        //mapUI settings
        check_person_locate_mapview.map.uiSettings.isRotateGesturesEnabled = false
        check_person_locate_mapview.map.uiSettings.isOverlookingGesturesEnabled = false
        check_person_locate_mapview.map.uiSettings.isCompassEnabled = false
        //
//        check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_blue)).position(mLatLngBlue))
//        check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_red)).position(mLatLngRed))
//        check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_yellow)).position(mLatLngYellow))
    }

    companion object {
        fun newInstance() = CheckPersonLocateFragment()
    }
}
