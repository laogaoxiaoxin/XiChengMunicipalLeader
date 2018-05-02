package com.lovelyjiaming.municipalleader.views.fragments.engineer

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.views.adapter.EngineerSameAllAdapter
import kotlinx.android.synthetic.main.fragment_engineer_current.*

class EngineerCurrentFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_engineer_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engineer_usual_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        engineer_usual_recyclerview.adapter = EngineerSameAllAdapter(activity as Context)
    }

    companion object {
        fun newInstance() = EngineerCurrentFragment()
    }
}
