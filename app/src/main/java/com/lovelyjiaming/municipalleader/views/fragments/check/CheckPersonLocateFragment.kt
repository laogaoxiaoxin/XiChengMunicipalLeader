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
import com.baidu.mapapi.utils.CoordinateConverter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.*
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS
import kotlinx.android.synthetic.main.fragment_check_person_locate.*

class CheckPersonLocateFragment : Fragment() {

    private var locateList: List<InspectCurrentLocationItem>? = null
    //所有人员信息
    private var personInfoList: MutableList<InspectPersonInfoItemClass>? = null
    //
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //先请求所有人信息
        XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getAllPersonInfo", {
            personInfoList = Gson().fromJson(it, InspectPersonInfoClass::class.java).InspectPersonInfo.toMutableList()
        })
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_check_person_locate, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMapView(savedInstanceState)
        //
        handler.postDelayed(object : Runnable {
            override fun run() {
                requestPersonLocation()
                handler.postDelayed(this, 7500L)
            }
        }, 7500L)
        requestPersonLocation()
    }

    fun requestPersonLocation() {
        //网络请求
        XCNetWorkUtil.invokeGetRequest(activity!!, NETWORK_BASIC_CHECK_ADDRESS + "currentLocation", {
            locateList = Gson().fromJson(it, InspectCurrentLocation::class.java).InspectCurrentLocation
            setMapInfo()
        })
    }

    //画点时得到人员信息，从另一个接口
    private fun getPersonInfoDrawPoint(userName: String): InspectPersonInfoItemClass? {
        val info = personInfoList?.filter {
            it.username == userName
        }
        return if (info != null && info.size != 0) info[0] else null
    }

    data class CtmpData(val latLng: LatLng, val userName: String)

    @SuppressLint("SetTextI18n")
    private fun setMapInfo() {
        if (personInfoList == null) return
        if (check_person_locate_mapview == null) return
        //坐标转换,构造新的结构
        val newLocateList = locateList?.map {
            val converter = CoordinateConverter()
            converter.from(CoordinateConverter.CoordType.COMMON)
            converter.coord(LatLng(it.x?.toDouble()!!, it.y?.toDouble()!!))
            CtmpData(converter.convert(), it.userName!!)
        }
        check_person_locate_mapview.map.clear()
        //开始插入图标
        newLocateList?.forEach {
            when (getPersonInfoDrawPoint(it.userName)?.WorkType) {
                "架空线" -> check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_yellow)).position(it.latLng))
                "审批掘路" -> check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_blue)).position(it.latLng))
                "道路巡查" -> check_person_locate_mapview.map.addOverlay(MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_location_red)).position(it.latLng))
            }
        }
        //点击弹出气泡事件
        check_person_locate_mapview.map.setOnMarkerClickListener {
            val latitude = it.position.latitude
            val longitude = it.position.longitude
            val itemInfo = newLocateList?.filter { it.latLng.latitude == latitude && it.latLng.longitude == longitude }
            //
            if (itemInfo != null && itemInfo.size != 0) {
                val linearLayout = LinearLayout(activity)
                linearLayout.orientation = LinearLayout.HORIZONTAL
                linearLayout.setPadding(20, 20, 10, 50)
                linearLayout.setBackgroundResource(R.drawable.popup)
                linearLayout.gravity = Gravity.CENTER_VERTICAL
                linearLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //头像
                val headImg = ImageView(activity)
                headImg.layoutParams = LinearLayout.LayoutParams(150, 150)
                Glide.with(activity!!).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + (getPersonInfoDrawPoint(itemInfo[0].userName)?.headaculpture
                        ?: "")).apply(RequestOptions().placeholder(R.drawable.default_head)).into(headImg)
                headImg.scaleType = ImageView.ScaleType.FIT_XY
                linearLayout.addView(headImg)
                //信息
                val popupText = TextView(activity)
                popupText.setTextColor(Color.BLACK)
                popupText.setPadding(20, 20, 20, 20)
                popupText.textSize = 12f
                popupText.text = "姓名：${getPersonInfoDrawPoint(itemInfo[0].userName)?.username}\r\n电话：${getPersonInfoDrawPoint(itemInfo[0].userName)?.phonenumber}"
                linearLayout.addView(popupText)
                //
                check_person_locate_mapview.map.showInfoWindow(InfoWindow(linearLayout, it.position, -61))
            }
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
