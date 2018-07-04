package com.lovelyjiaming.municipalleader.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.activitys.ExamRoadDetailHelpItem
import com.lovelyjiaming.municipalleader.views.activitys.LargePicActivity

class FeedbackExamRoadHistoryAdapter(private val ctx: Context) : RecyclerView.Adapter<FeedbackExamRoadHistoryAdapter.ViewHolder>() {
    private var listPicData: MutableList<ExamRoadDetailHelpItem>? = null

    fun setPicData(list: List<ExamRoadDetailHelpItem>) {
        this.listPicData?.clear()
        this.listPicData = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_feedback_examroad_historypic, parent, false))

    override fun getItemCount(): Int = listPicData?.size ?: 0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            item_examroad_historypic_time.text = listPicData?.get(position)?.date
            Glide.with(ctx).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.first).into(item_examroad_historypic_1)
            Glide.with(ctx).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.second).into(item_examroad_historypic_2)
            Glide.with(ctx).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.third).into(item_examroad_historypic_3)
            Glide.with(ctx).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.fourth).into(item_examroad_historypic_4)
            item_examroad_historypic_remark.text = "备注:${listPicData?.get(position)?.examRemark} "
            //
            val listLarge = arrayListOf(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.first, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.second,
                    XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.third, XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listPicData?.get(position)?.fourth)
            item_examroad_historypic_1.setOnClickListener {
                val intent = Intent(ctx, LargePicActivity::class.java)
                intent.putExtra("picsurl", listLarge)
                intent.putExtra("index", 0)
                ctx.startActivity(intent)
            }
            item_examroad_historypic_2.setOnClickListener {
                val intent = Intent(ctx, LargePicActivity::class.java)
                intent.putExtra("picsurl", listLarge)
                intent.putExtra("index", 1)
                ctx.startActivity(intent)
            }
            item_examroad_historypic_3.setOnClickListener {
                val intent = Intent(ctx, LargePicActivity::class.java)
                intent.putExtra("picsurl", listLarge)
                intent.putExtra("index", 2)
                ctx.startActivity(intent)
            }
            item_examroad_historypic_4.setOnClickListener {
                val intent = Intent(ctx, LargePicActivity::class.java)
                intent.putExtra("picsurl", listLarge)
                intent.putExtra("index", 3)
                ctx.startActivity(intent)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        //
        val item_examroad_historypic_time: TextView by lazy {
            itemView.findViewById(R.id.item_examroad_historypic_time) as TextView
        }
        val item_examroad_historypic_1: ImageView by lazy {
            itemView.findViewById(R.id.item_examroad_historypic_1) as ImageView
        }
        val item_examroad_historypic_2: ImageView by lazy {
            itemView.findViewById(R.id.item_examroad_historypic_2) as ImageView
        }
        val item_examroad_historypic_3: ImageView by lazy {
            itemView.findViewById(R.id.item_examroad_historypic_3) as ImageView
        }
        val item_examroad_historypic_4: ImageView by lazy {
            itemView.findViewById(R.id.item_examroad_historypic_4) as ImageView
        }
        val item_examroad_historypic_remark: TextView by lazy {
            itemView.findViewById(R.id.item_examroad_historypic_remark) as TextView
        }
    }
}