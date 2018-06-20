package com.lovelyjiaming.municipalleader.views.fragments.check

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.R
import kotlinx.android.synthetic.main.fragment_check_person_track.*

class CheckPersonTrackFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_person_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        check_person_track_mapview.onCreate(activity, savedInstanceState)
        //mapUI settings
        check_person_track_mapview.map.uiSettings.isRotateGesturesEnabled = false
        check_person_track_mapview.map.uiSettings.isOverlookingGesturesEnabled = false
        check_person_track_mapview.map.uiSettings.isCompassEnabled = false
    }

    override fun onResume() {
        super.onResume()
        check_person_track_mapview.onResume()
    }

    override fun onPause() {
        super.onPause()
        check_person_track_mapview.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        check_person_track_mapview.onDestroy()
    }


    companion object {
        fun newInstance() = CheckPersonTrackFragment()
    }
}
