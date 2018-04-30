package com.lovelyjiaming.municipalleader.utils

import android.support.v4.app.FragmentActivity
import okhttp3.*
import java.io.IOException

//巡查部分 undone
data class InspectUndoneClass(val name: String?, val number: String?, val date: String?, val place: String?, val rank: String?, val office: String?, val state: String?, val longitude: String?, val latitude: String?,
                              val picfirst: String, val picsecond: String?, val picthird: String?, val type: String?, val assigngroup: String?, val assigndate: String?)

data class CheckUndoneClass(val InspectUndone: List<InspectUndoneClass>)

object XCNetWorkUtil {
    const val NETWORK_BASIC_ADDRESS = "http://39.104.80.111:8888/RoadLeader/InspectServlet?method="

    fun invokeGetRequest(activity: FragmentActivity, url: String, listener: (String) -> Unit, mapParams: HashMap<String, String>? = null) {
        val finalUrlSb = StringBuilder(url)
        mapParams?.forEach {
            finalUrlSb.append("${it.key}=${it.value}")
            finalUrlSb.append("&")
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