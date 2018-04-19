package com.lovelyjiaming.municipalleader.views.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (save_whole_pie != null && isVisibleToUser) {
            save_whole_pie.visibility = View.VISIBLE
            save_whole_pie.startAnimateDraw()
        } else if (save_whole_pie != null) save_whole_pie.visibility = View.INVISIBLE
    }

    companion object {
        fun newInstance() = SaveWholeCalcuFragment()
    }
}
