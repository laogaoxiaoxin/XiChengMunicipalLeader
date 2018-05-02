package com.lovelyjiaming.municipalleader.utils

import android.support.v4.app.FragmentActivity
import okhttp3.*
import java.io.IOException
import java.io.Serializable

//巡查部分-locate
data class InspectLocationItemClass(val username: String, val department: String?, val authority: String?, val phonenumber: String?, val WorkType: String, val longitude: String, val latitude: String)

data class InspectLocationClass(val InspectLocation: List<InspectLocationItemClass>)

//巡查部分-calc
data class InspectCaseCountItemClass(val taskName: String?, val taskDate: String?)

data class InspectCaseCountClass(val unfinished: Int, val finished: Int, val InspectCaseCount: List<InspectCaseCountItemClass>)

//1.巡查部分-undone 2.养护部分-onlinetask
data class InspectUndoneItemClass(val taskName: String?, val taskNumber: String?, val taskDate: String?, val taskPlace: String?, val taskRank: String?, val taskOffice: String?, val taskState: String?,
                                  val taskLongitude: String?, val taskLatitude: String?, val taskFirst: String, val taskSecond: String?, val taskThird: String?, val taskType: String?, val taskAssign: String?, val taskAssignDate: String?,
                                  val taskAsphalt_9cm_10: String?, val taskAsphalt_5cm_10: String?, val taskAsphalt_9cm_400: String?, val taskAsphalt_5cm_400: String?, val asphalt_9cm_10_400: String?, val asphalt_5cm_10_400: String?,
                                  val taskSidewalk: String?, val taskPlaster: String?, val taskRainwater_outlet: String?, val caiselumian: String?) : Serializable

data class InspectUndoneClass(val InspectUndone: List<InspectUndoneItemClass>)

//养护部分 onlinetask
data class CureOnLineTaskClass(val CureOnLineTask: List<InspectUndoneItemClass>)

//应急部分 抢险案件
data class EmergencyTaskItemClass(val IsFinish: String?, val taskName: String?, val taskNumber: String?, val taskDate: String?, val taskPlace: String?, val taskHome: String?, val taskOffice: String?, val taskState: String?,
                                  val taskFirst: String?, val taskSecond: String?, val taskThird: String?, val taskType: String?, val SecondDate: String?) : Serializable

data class EmergencyTask(val EmergencyTask: List<EmergencyTaskItemClass>)

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
object XCNetWorkUtil {
    //巡查基地址
    const val NETWORK_BASIC_CHECK_ADDRESS = "http://39.104.80.111:8888/RoadLeader/InspectServlet?method="
    //养护基地址
    const val NETWORK_BASIC_SAVE_ADDRESS = "http://39.104.80.111:8888/RoadLeader/CureServlet?method="
    //应急基地址
    const val NETWORK_BASIC_DANGER_ADDRESS = "http://39.104.80.111:8888/RoadLeader/EmergencyServlet?method="
    //图片前缀
    const val NETWORK_IMG_BASIC_ADDRESS = "http://39.104.80.111:8888/lalio/"

    fun invokeGetRequest(activity: FragmentActivity, url: String, listener: (String) -> Unit, mapParams: HashMap<String, String>? = null) {
        val finalUrlSb = StringBuilder(url)
        mapParams?.forEach {
            finalUrlSb.append("&")
            finalUrlSb.append("${it.key}=${it.value}")
        }

        val request = Request.Builder().get().url(finalUrlSb.toString()).build()
        val call = OkHttpClient().newCall(request)
        //
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
                val response = response?.body()?.string()!!
                activity.runOnUiThread {
                    listener(response)
                }
            }

        })
    }
}