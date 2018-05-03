package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.EngineerItem
import com.lovelyjiaming.municipalleader.views.activitys.EngineerDetailActivity

class EngineerSameAllAdapter constructor(private val ctx: Context) : RecyclerView.Adapter<EngineerSameAllAdapter.ViewHolder>() {
    var listData: List<EngineerItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_engineer_same_all, parent, false))
        return viewHolder
    }

    override fun getItemCount(): Int = listData?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listData?.let {
            holder.engineer_name.text = "工程名称：" + listData?.get(position)?.name
            holder.engineer_time.text = "工程开工时间：" + listData?.get(position)?.start

            //
            holder.itemView.setOnClickListener {
                val intent = Intent(ctx, EngineerDetailActivity::class.java)
                intent.putExtra("enginfo", listData?.get(position))
                ctx.startActivity(intent)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val engineer_name: TextView by lazy {
            itemView.findViewById(R.id.engineer_name) as TextView
        }
        val engineer_time: TextView by lazy {
            itemView.findViewById(R.id.engineer_time) as TextView
        }
    }
}


