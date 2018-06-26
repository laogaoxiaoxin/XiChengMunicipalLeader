package com.lovelyjiaming.municipalleader.views.activitys

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import kotlinx.android.synthetic.main.activity_large_pic.*
import uk.co.senab.photoview.PhotoView

class LargePicActivity : AppCompatActivity() {
    val listImageView: ArrayList<ImageView> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_large_pic)
        AutoUtils.auto(this)
        all_large_pics_close.setOnClickListener { finish() }
        //
        val listPics = intent.getSerializableExtra("picsurl") as ArrayList<*>
        listPics.forEachIndexed { index, _ ->
            val imgView = PhotoView(this)
            imgView.scaleType = ImageView.ScaleType.CENTER_CROP
            imgView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            Glide.with(this).load(listPics[index]).into(imgView)
            listImageView.add(imgView)
        }
        all_large_pics_viewpager.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
            override fun getCount(): Int = listPics.size
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(listImageView[position])
                return listImageView[position]
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        all_large_pics_viewpager.setCurrentItem(intent.getIntExtra("index", 0), false)
    }

}
