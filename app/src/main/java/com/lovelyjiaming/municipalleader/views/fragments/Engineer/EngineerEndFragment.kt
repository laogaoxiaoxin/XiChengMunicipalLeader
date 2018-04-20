package com.lovelyjiaming.municipalleader.views.fragments.Engineer


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.views.adapter.EngineerSameAllAdapter
import kotlinx.android.synthetic.main.fragment_engineer_end.*

class EngineerEndFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_engineer_end, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engineer_end_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        engineer_end_recyclerview.adapter = EngineerSameAllAdapter(activity as Context)
    }

    companion object {
        fun newInstance() =
                EngineerEndFragment()
    }
}