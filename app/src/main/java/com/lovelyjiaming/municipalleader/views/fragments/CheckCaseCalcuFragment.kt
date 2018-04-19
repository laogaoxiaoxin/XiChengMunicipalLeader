package com.lovelyjiaming.municipalleader.views.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.views.adapter.CheckCalcuCaseAdapter
import kotlinx.android.synthetic.main.fragment_check_case_calcu.*


class CheckCaseCalcuFragment : Fragment() {

    //for spinner
    val mArrForSel: ArrayList<String> =
            arrayListOf("案件类型选择1", "案件类型选择2", "案件类型选择3", "案件类型选择4", "案件类型选择5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_case_calcu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        val arrayAdapterSel = ArrayAdapter<String>(activity, R.layout.custom_spinner_text_item, mArrForSel)
        arrayAdapterSel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //
        check_case_calcu_type.adapter = arrayAdapterSel
        check_case_calcu_type.prompt = "请在这里选择案件类型"
        //
        check_case_calcu_address.adapter = arrayAdapterSel
        check_case_calcu_address.prompt = "请在这里选择案件地址"
        //
        check_case_calcu_duration.adapter = arrayAdapterSel
        check_case_calcu_duration.prompt = "请在这里选择案件周期"
        //
        check_case_calcu_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        check_case_calcu_recyclerview.adapter = CheckCalcuCaseAdapter(activity as Context)
    }

    companion object {
        fun newInstance() =
                CheckCaseCalcuFragment()
    }
}
