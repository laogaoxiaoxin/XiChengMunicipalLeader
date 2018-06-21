package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.R.id.*
import com.lovelyjiaming.municipalleader.utils.InspectLocationItemClass

class PersonListTrackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_list_track)

        //
        person_list_track_title.text = intent.getStringExtra("groupname") + "组人员轨迹"
        val arrayInspect = intent.getSerializableExtra("personlist") as Array<InspectLocationItemClass>
        val arrayName = arrayInspect.map { it.username }
        listview_person_list_track.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayName.filter { !it.contains("领导") && !it.contains("员工") })
        //
        person_list_track_back.setOnClickListener { finish() }
        //
        listview_person_list_track.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent()
            intent.putExtra("name", (view as TextView).text.toString())
            setResult(1077, intent)
            finish()
        }
    }
}
