package com.lovelyjiaming.municipalleader.utils

import android.support.v4.app.FragmentActivity
import okhttp3.*
import java.io.IOException
import java.io.Serializable

//巡查部分-巡查员工个人信息
data class InspectPersonInfoItemClass(val username: String, val department: String?, val authority: String?, val phonenumber: String?, val WorkType: String, val headaculpture: String?) : Serializable

data class InspectPersonInfoClass(val InspectPersonInfo: List<InspectPersonInfoItemClass>)
//
data class InspectTrackItem(val x: String?, val y: String?, val time: String?)

data class InspectTrack(val InspectTrack: MutableList<InspectTrackItem>)

//巡查部分-巡查员工位置信息
data class InspectCurrentLocationItem(val userName: String?, val x: String?, val y: String?)

data class InspectCurrentLocation(val InspectCurrentLocation: List<InspectCurrentLocationItem>)

//巡查部分-calc
data class InspectCaseCountItemClass(val taskName: String?, val taskDate: String?)

data class InspectCaseCountClass(val unfinished: Int, val finished: Int, val InspectCaseCount: List<InspectCaseCountItemClass>?)

//1.巡查部分-undone 2.养护部分-onlinetask
data class InspectUndoneItemClass(val taskName: String?, val taskNumber: String?, val taskDate: String?, val taskPlace: String?, val taskRank: String?, val taskOffice: String?, val taskState: String?,
                                  val taskLongitude: String?, val taskLatitude: String?, val taskFirst: String, val taskSecond: String?, val taskThird: String?, val taskType: String?, val taskAssign: String?, val taskAssignDate: String?,
                                  val taskAsphalt_9cm_10: String?, val taskAsphalt_5cm_10: String?, val taskAsphalt_9cm_400: String?, val taskAsphalt_5cm_400: String?, val asphalt_9cm_10_400: String?,
                                  val asphalt_5cm_10_400: String?, val taskSidewalk: String?, val taskPlaster: String?, val taskRainwater_outlet: String?, val caiselumian: String?, val taskCurb: String?,
                                  val taskMachine_stone: String?, val taskInorganic_material_15cm: String?, val taskInorganic_material_20cm: String?, val taskTree_pool: String?, val mangdao: String?,
                                  val shengjiangjianchajing: String?, val jiagujianchajing: String?, val wujiliao25: String?, val shicaibudao: String?, val shicaimangdao: String?, val shicailuyuanshi: String?, val shicaidangchezhuang: String?, val tiezhidangchezhuang: String?,
                                  val prefirst: String?, val presecond: String?, val prethird: String?, val underthird: String?, val undersecond: String?, val underfirst: String?, val afterfirst: String?, val aftersecond: String?, val afterthird: String?,
                                  val finishFirst: String?, val finishSecond: String?, val finishThird: String?, val taskNote: String?, val taskRemarks: String?) : Serializable

data class InspectUndoneClass(val InspectUndone: List<InspectUndoneItemClass>)

//养护部分 onlinetask
data class CureOnLineTaskClass(val CureOnLineList: List<InspectUndoneItemClass>)

//养护部分 onlinetask
data class CureAppliedTaskClass(val CureAppliedList: List<InspectUndoneItemClass>)

//养护详情
data class CureDetailInfo(val CureOnLineDetails: InspectUndoneItemClass)

//养护统计
data class MaterailItemClass(val taskAsphalt_9cm_10: String?, val taskAsphalt_5cm_10: String?, val asphalt_9cm_10_400: String?, val asphalt_5cm_10_400: String?, val taskAsphalt_9cm_400: String?, val taskAsphalt_5cm_400: String?
                             , val taskSidewalk: String?, val mangdao: String?, val taskPlaster: String?, val caiselumian: String?, val shengjiangjianchajing: String?, val jiagujianchajing: String?, val taskRainwater_outlet: String?, val taskCurb: String?
                             , val taskMachine_stone: String?, val taskInorganic_material_15cm: String?, val taskInorganic_material_20cm: String?, val wujiliao25: String?, val taskTree_pool: String?, val shicaibudao: String?, val shicaimangdao: String?, val shicailuyuanshi: String?
                             , val shicaidangchezhuang: String?, val tiezhidangchezhuang: String?, val taskconcrete: String?)

data class MaterialClass(val result: Int, val CaseCount: MutableList<MaterailItemClass>)

//应急部分 抢险案件
data class EmergencyTaskItemClass(val IsFinish: String?, val taskName: String?, val taskNumber: String?, val taskDate: String?, val taskPlace: String?, val taskHome: String?, val taskOffice: String?, val taskState: String?,
                                  val taskFirst: String?, val taskSecond: String?, val taskThird: String?, val taskType: String?, val FirstDate: String?,
                                  val taskAsphalt_9cm_10: String, val taskAsphalt_5cm_10: String, val asphalt_9cm_10_400: String, val asphalt_5cm_10_400: String, val taskAsphalt_9cm_400: String, val taskAsphalt_5cm_400: String, val taskSidewalk: String?,
                                  val taskInorganic_material_15cm: String?, val taskInorganic_material_20cm: String?, val taskInorganic_material_25cm: String?, val luyuanshi: String?, val jianchajing: String?, val mohuimianji: String?, val mohuihoudu: String, val guanchang: String?, val guanjing: String?, val guancai: String?,
                                  val prefirst: String?, val presecond: String?, val prethird: String?, val underfirst: String?, val undersecond: String?, val underthird: String?, val afterfirst: String?, val aftersecond: String?, val afterthird: String?) : Serializable

data class EmergencyTask(val EmergencyTask: List<EmergencyTaskItemClass>)

//应急 统计
data class EmergencyCarItem(val Id: String?, val carId: String?, val carType: String?, val Type: String?)

data class EmergencyCarClass(val EmergencyCar: List<EmergencyCarItem>)
//
data class EmergencyMechItem(val Id: String?, val carName: String?, val carType: String?)

data class EmergencyMechClass(val EmergencyMech: List<EmergencyMechItem>)
//
data class EmergencysmallMechItem(val Id: String?, val DeviceName: String?, val Number: String?)

data class EmergencysmallMechClass(val EmergencysmallMech: List<EmergencysmallMechItem>)
//
data class EmergencySuppiliesItem(val Id: String?, val SupplyName: String?, val Number: String?)

data class EmergencySuppiliesClass(val EmergencySuppilies: List<EmergencySuppiliesItem>)

//工程
data class EngineerItem(val name: String?, val number: String?, val condition: String?, val type: String?, val person: String?, val telephone: String?, val start: String?, val done: String?,
                        val introduction: String?, val pace: String?, val proportion: String, val remarks: String?, val picfirst: String?, val picsecond: String?, val picthird: String?) : Serializable

data class EngineerGeneralClass(val EngineerGeneral: List<EngineerItem>)
data class EngineerMajorClass(val EngineerMajor: List<EngineerItem>)

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
object XCNetWorkUtil {
    //巡查基地址
    const val NETWORK_BASIC_CHECK_ADDRESS = "http://39.104.80.111:8888/RoadLeader/InspectServlet?method="
    //养护基地址
    const val NETWORK_BASIC_SAVE_ADDRESS = "http://39.104.80.111:8888/RoadLeader/CureServlet?method="
    //应急基地址
    const val NETWORK_BASIC_DANGER_ADDRESS = "http://39.104.80.111:8888/RoadLeader/EmergencyServlet?method="
    //工程基地址
    const val NETWORK_BASIC_ENGINEER_ADDRESS = "http://39.104.80.111:8888/RoadLeader/EngineeringServlet?method="
    //图片前缀
    const val NETWORK_IMG_BASIC_ADDRESS = "http://39.104.80.111:8888/lalio/"

    private lateinit var mNewCall: Call

    fun invokeGetRequest(activity: FragmentActivity?, url: String, listener: (String) -> Unit, mapParams: HashMap<String, String>? = null) {
        val finalUrlSb = StringBuilder(url)
        mapParams?.forEach {
            finalUrlSb.append("&")
            finalUrlSb.append("${it.key}=${it.value}")
        }

        val request = Request.Builder().get().url(finalUrlSb.toString()).build()
        mNewCall = OkHttpClient().newCall(request)
        //
        mNewCall.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
                val response = response?.body()?.string()!!
                activity!!.runOnUiThread {
                    listener(response)
                }
            }

        })
    }

    fun cancelRequest() {
        mNewCall.cancel()
    }
}