package com.lovelyjiaming.municipalleader.views.customdraw

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator

class CustomDrawHorizontalColumn(private val ctx: Context, val attr: AttributeSet) : View(ctx, attr) {
    lateinit var mAllMapData: HashMap<String, Int>
    var allLineWidthInc: ArrayList<Float> = arrayListOf()
    var mAnimatorSet: AnimatorSet? = null
    var mPaintLine: ArrayList<Paint> = arrayListOf()
    var mPaintText: ArrayList<Paint> = arrayListOf()
    var mPaintColors: ArrayList<String> = arrayListOf("#A1378B", "#79CDCD", "#A2CD5A", "#A0522D", "#00CD66", "#CD6600", "#CD950C", "#B22222", "#8A2BE2", "#212121",
            "#A1378B", "#79CDCD", "#A2CD5A", "#A0522D", "#00CD66", "#CD6600", "#CD950C", "#B22222", "#8A2BE2", "#212121")
    var mAllLineHeightInterval: ArrayList<Float> = ArrayList(50)

    fun setAllDatas(mapData: HashMap<String, Int>) {
        //
        mAnimatorSet = null
        mAllLineHeightInterval.clear()
        mPaintLine.clear()
        mPaintText.clear()
        allLineWidthInc.clear()

        this.mAllMapData = mapData
        //find max base
        val maxBase = mAllMapData.maxBy { it.value }?.value!!
        //calc each width and height
        val eachLineInterval = measuredHeight.toFloat() / mAllMapData.size.toFloat()
        var eachIntervalTmp = 5f
        mAllMapData.forEach { _, i ->
            //w
            val eachWidth = (i.toFloat() / maxBase.toFloat()) * (measuredWidth - 150)
            allLineWidthInc.add(eachWidth)
            //h
            mAllLineHeightInterval.add(eachIntervalTmp)
            eachIntervalTmp += eachLineInterval
        }
        createPaints()
        createAnimators()
    }

    private fun createPaints() {
        allLineWidthInc.forEachIndexed { index, _ ->
            val linePaint = Paint()
            linePaint.isAntiAlias = true
            linePaint.color = Color.parseColor(mPaintColors[index])
            linePaint.style = Paint.Style.STROKE
            linePaint.strokeCap = Paint.Cap.SQUARE
            linePaint.strokeWidth = 5F
            mPaintLine.add(linePaint)
            //
            val textPaint = Paint()
            textPaint.isAntiAlias = true
            textPaint.color = Color.parseColor(mPaintColors[index])
            textPaint.textSize = 25f
            textPaint.textAlign = Paint.Align.LEFT
            mPaintText.add(textPaint)
        }

    }

    private fun createAnimators() {
        //create each valueAnimator
        mAnimatorSet = AnimatorSet()
        var aBuilder: AnimatorSet.Builder? = null
        allLineWidthInc.forEachIndexed { index, fl ->
            val animate = ValueAnimator.ofFloat(150f, fl + 150)
            animate.interpolator = AnticipateOvershootInterpolator()
            animate.addUpdateListener {
                allLineWidthInc[index] = it.animatedValue as Float
                invalidate()
            }
            if (index == 0)
                aBuilder = mAnimatorSet?.play(animate)
            else
                aBuilder?.with(animate)
        }
        mAnimatorSet?.duration = 1000
        mAnimatorSet?.start()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //
        mAllLineHeightInterval.forEachIndexed { index, interval ->
            canvas?.drawLine(150f, interval, allLineWidthInc[index], interval, mPaintLine[index])
            canvas?.drawText(mAllMapData.keys.toMutableList()[index], 0f, interval + 15, mPaintText[index])
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
}