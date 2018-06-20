package com.lovelyjiaming.municipalleader.views.fragments.check

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.map.InfoWindow
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.model.LatLng
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.InspectLocationClass
import com.lovelyjiaming.municipalleader.utils.InspectLocationItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.fragment_check_person_locate.*

class CheckPersonLocateFragment : Fragment() {

    lateinit var locateList: List<InspectLocationItemClass>
    //
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler.postDelayed(object : Runnable {
            override fun run() {
                XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getLocation", {
                    locateList = Gson().fromJson(it, InspectLocationClass::class.java).InspectLocation.filter {
                        it.department?.equals("patrol") ?: false
                    }
                    setMapInfo()
                })
                handler.postDelayed(this, 10000L)
            }
        }, 10000L)
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
        handler.removeCallbacksAndMessages(null)
        check_person_locate_mapview.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_check_person_locate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapView(savedInstanceState)
        //网络请求
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getLocation", {
            locateList = Gson().fromJson(it, InspectLocationClass::class.java).InspectLocation.filter {
                it.department?.equals("patrol") ?: false
            }
            setMapInfo()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setMapInfo() {
        check_person_locate_mapview.map.clear()
        locateList.forEach {
            when (it.WorkType) {
                "架空线" -> check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_yellow)).position(LatLng(it.latitude.toDouble(), it.longitude.toDouble())))
                "审批掘路" -> check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_blue)).position(LatLng(it.latitude.toDouble(), it.longitude.toDouble())))
                "道路巡查" -> check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_red)).position(LatLng(it.latitude.toDouble(), it.longitude.toDouble())))
            }
        }
        //点击弹出气泡事件
        check_person_locate_mapview.map.setOnMarkerClickListener {
            val latitude = it.position.latitude
            val longitude = it.position.longitude
            val itemInfo = locateList.filter { it.latitude.toDouble() == latitude && it.longitude.toDouble() == longitude }
            //
            val linearLayout = LinearLayout(activity)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.setPadding(30, 10, 30, 50)
            linearLayout.setBackgroundResource(R.drawable.popup)
            linearLayout.gravity = Gravity.CENTER_VERTICAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200)
            //
            val headImg = ImageView(activity)
            headImg.layoutParams = LinearLayout.LayoutParams(150, 150)
            Glide.with(activity!!).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + itemInfo[0].headaculpture).into(headImg)
            headImg.scaleType = ImageView.ScaleType.FIT_XY
            linearLayout.addView(headImg)
            //
            val popupText = TextView(activity)
            popupText.setTextColor(Color.BLACK)
            popupText.setPadding(20, 20, 20, 20)
            popupText.textSize = 12f
            popupText.text = "姓名：${itemInfo[0].username}\r\n电话：${itemInfo[0].phonenumber}"
            linearLayout.addView(popupText)
            AutoUtils.auto(linearLayout)
            //
            check_person_locate_mapview.map.showInfoWindow(InfoWindow(linearLayout, it.position, -60))
            true
        }
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        check_person_locate_mapview.onCreate(activity, savedInstanceState)
        //mapUI settings
        check_person_locate_mapview.map.uiSettings.isRotateGesturesEnabled = false
        check_person_locate_mapview.map.uiSettings.isOverlookingGesturesEnabled = false
        check_person_locate_mapview.map.uiSettings.isCompassEnabled = false
    }

    companion object {
        fun newInstance() = CheckPersonLocateFragment()
    }
}
