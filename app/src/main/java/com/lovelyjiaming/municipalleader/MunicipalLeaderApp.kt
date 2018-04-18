package com.lovelyjiaming.municipalleader

import android.app.Application
import com.baidu.mapapi.SDKInitializer

class MunicipalLeaderApp : Application() {

    override fun onCreate() {
        SDKInitializer.initialize(this)

        super.onCreate()
    }
}