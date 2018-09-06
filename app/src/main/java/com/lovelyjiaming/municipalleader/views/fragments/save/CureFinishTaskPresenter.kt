package com.lovelyjiaming.municipalleader.views.fragments.save

import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil

class CureFinishTaskPresenter(private val fragment: SaveFinishTaskFragment) {

    fun requestData() {
        XCNetWorkUtil.invokeGetRequest(fragment.activity, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getAppliedList", {
            fragment.setNetResultData(it)
        }, hashMapOf())
    }


}