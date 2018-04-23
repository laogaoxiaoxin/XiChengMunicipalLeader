package com.lovelyjiaming.municipalleader.views.fragments.check

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import com.lovelyjiaming.municipalleader.views.adapter.CheckCalcuCaseAdapter
import kotlinx.android.synthetic.main.fragment_check_case_calcu.*
import java.util.*

class CheckCaseCalcuFragment : Fragment() {

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
        check_case_calcu_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        check_case_calcu_recyclerview.adapter = CheckCalcuCaseAdapter(activity as Context)
        //
        setClickListener()
    }

    private fun setClickListener() {
        //
        check_case_calcu_startdate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context) { check_case_calcu_startdate.text = it }
        }
        //
        check_case_calcu_enddate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context) { check_case_calcu_enddate.text = it }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) check_case_calcu_ring.startRingAnimation()
    }

    companion object {
        fun newInstance() = CheckCaseCalcuFragment()
    }
}
