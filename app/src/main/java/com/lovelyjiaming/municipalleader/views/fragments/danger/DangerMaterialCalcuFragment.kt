package com.lovelyjiaming.municipalleader.views.fragments.danger


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.views.adapter.SaveWholeCalcuAdapter
import kotlinx.android.synthetic.main.fragment_danger_material_calcu.*

class DangerMaterialCalcuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danger_material_calcu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        danger_material_calc_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        danger_material_calc_recyclerview.adapter = SaveWholeCalcuAdapter(activity as Context)//暂时
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (danger_type_calc_pie != null && isVisibleToUser) {
            danger_type_calc_pie.startAnimateDraw()
            danger_type_calc_pie.visibility = View.VISIBLE
        } else if (danger_type_calc_pie != null) danger_type_calc_pie.visibility = View.INVISIBLE
    }

    companion object {
        fun newInstance() = DangerMaterialCalcuFragment()
    }
}
