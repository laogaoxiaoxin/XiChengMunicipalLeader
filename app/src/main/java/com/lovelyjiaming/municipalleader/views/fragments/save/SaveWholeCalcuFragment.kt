package com.lovelyjiaming.municipalleader.views.fragments.save

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.DatePickerUtils
import com.lovelyjiaming.municipalleader.views.adapter.SaveWholeCalcuAdapter
import kotlinx.android.synthetic.main.fragment_save_whole_calcu.*

class SaveWholeCalcuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_whole_calcu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_whole_recyclerview.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        save_whole_recyclerview.adapter = SaveWholeCalcuAdapter(activity as Context)
        //
        save_whole_top_startdate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context, { save_whole_top_startdate.text = it })
        }
        save_whole_top_enddate.setOnClickListener {
            DatePickerUtils.displayDatePickerDialog(activity as Context, { save_whole_top_enddate.text = it })
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        val hashTest = LinkedHashMap<String, Int>()
        hashTest.put("1", 1000)
        hashTest.put("2", 12)
        hashTest.put("3", 13)
        hashTest.put("4", 444)
        hashTest.put("5", 555)
        hashTest.put("6", 666)
        hashTest.put("7", 777)
        hashTest.put("8", 888)
        hashTest.put("9", 999)
        hashTest.put("10", 123)
        hashTest.put("11", 421)
        hashTest.put("12", 321)
        hashTest.put("13", 666)
        hashTest.put("14", 78)
        hashTest.put("15", 344)
        hashTest.put("16", 217)
        hashTest.put("17", 678)
        hashTest.put("18", 789)
        hashTest.put("19", 33)
        hashTest.put("20", 1000)

        if (save_whole_vertical_chart != null && isVisibleToUser) {
            save_whole_vertical_chart.visibility = View.VISIBLE
            save_whole_vertical_chart.startAllData(hashTest)
        } else if (save_whole_vertical_chart != null) save_whole_vertical_chart.visibility = View.INVISIBLE
    }

    companion object {
        fun newInstance() = SaveWholeCalcuFragment()
    }
}
