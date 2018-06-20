package com.lovelyjiaming.municipalleader

import android.app.Application
import com.baidu.mapapi.SDKInitializer
import com.tencent.bugly.crashreport.CrashReport

class MunicipalLeaderApp : Application() {

    override fun onCreate() {
        SDKInitializer.initialize(this)

        super.onCreate()
        CrashReport.initCrashReport(applicationContext, "8f2623c87e", true);

    }
}