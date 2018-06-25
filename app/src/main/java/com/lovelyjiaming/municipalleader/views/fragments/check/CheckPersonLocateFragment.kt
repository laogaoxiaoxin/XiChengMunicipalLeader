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
    //所有的定位人
    private var mLocateList: List<InspectCurrentLocationItem>? = null
    //所有人员信息
    private var mPersonInfoList: MutableList<InspectPersonInfoItemClass>? = null
    //
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //先请求所有人信息
        XCNetWorkUtil.invokeGetRequest(activity, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getAllPersonInfo", {
            mPersonInfoList = Gson().fromJson(it, InspectPersonInfoClass::class.java).InspectPersonInfo.toMutableList()
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
        //每十秒刷新一次人员位置
        handler.postDelayed(object : Runnable {
            override fun run() {
                requestPersonLocation()
                handler.postDelayed(this, 10000L)
            }
        }, 10000L)
        requestPersonLocation()
        //显示道路巡查组员工
        ll_group_patrol_road.setOnClickListener {
            displayChooseGroup("道路巡查")
        }
        ll_group_exam_road.setOnClickListener {
            displayChooseGroup("审批掘路")
        }
        ll_group_patrol_jiakongxian.setOnClickListener {
            displayChooseGroup("架空线")
        }
    }

    //选定显示某一组巡查员工
    private fun displayChooseGroup(groupName: String) {
        //所有人员信息
        val list1 = mPersonInfoList?.filter { it.WorkType == groupName }
        val list2 = mutableListOf<InspectCurrentLocationItem>()
        mLocateList?.forEach { it1 ->
            list1?.forEach {
                if (it.username == it1.userName) {
                    list2.add(it1)
                }
            }
        }
        setMapInfo(list2)
    }

    fun requestPersonLocation() {
        XCNetWorkUtil.invokeGetRequest(activity!!, NETWORK_BASIC_CHECK_ADDRESS + "currentLocation", {
            mLocateList = Gson().fromJson(it, InspectCurrentLocation::class.java).InspectCurrentLocation
            setMapInfo(mLocateList)
        })
    }

    //画点时得到人员信息，从另一个接口
    private fun getPersonInfoDrawPoint(userName: String): InspectPersonInfoItemClass? {
        val info = mPersonInfoList?.filter {
            it.username == userName
        }
        return if (info != null && info.size != 0) info[0] else null
    }

    data class CtmpData(val latLng: LatLng, val userName: String)

    @SuppressLint("SetTextI18n")
    private fun setMapInfo(list: List<InspectCurrentLocationItem>?) {
        if (mPersonInfoList == null) return
        if (check_person_locate_mapview == null) return
        //
        check_person_locate_mapview.map.clear()
        //坐标转换,构造新的结构
        val newLocateList = list?.map {
            val converter = CoordinateConverter()
            converter.from(CoordinateConverter.CoordType.COMMON)
            converter.coord(LatLng(it.x?.toDouble()!!, it.y?.toDouble()!!))
            CtmpData(converter.convert(), it.userName!!)
        }
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
