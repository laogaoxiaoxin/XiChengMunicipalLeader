package com.lovelyjiaming.municipalleader.views.customdraw

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator

class CustomDrawVerticalColumn(private val ctx: Context, val attr: AttributeSet) : View(ctx, attr) {
    //
    val listHeightInc: ArrayList<Float> = ArrayList(20)
    val listWidthInterval: ArrayList<Float> = ArrayList(20)
    var animatorSet: AnimatorSet? = null
    var mPaintColors: ArrayList<String> = arrayListOf("#A1378B", "#79CDCD", "#A2CD5A", "#A0522D", "#00CD66", "#CD6600", "#CD950C", "#B22222", "#8A2BE2", "#212121",
            "#A1371B", "#72CDCD", "#A9CD5A", "#A0577D", "#00CD11", "#CD2200", "#CD950C", "#B22222", "#8A2BE2", "#212121")
    var mPaintLine: ArrayList<Paint> = arrayListOf()
    var mPaintText: ArrayList<Paint> = arrayListOf()
    lateinit var mAllMapData: LinkedHashMap<String, Int>

    fun startAllData(mapParam: LinkedHashMap<String, Int>) {
        //
        animatorSet?.cancel()
        listHeightInc.clear()
        listWidthInterval.clear()
        //
        mAllMapData = mapParam
        //首先找出最大值，便于计算比例
        val maxVal = mapParam.maxBy { it.value }?.value?.toFloat()
        //x轴间隔
        val intervalX = measuredWidth / mapParam.size
        var startX = 6f
        mapParam.forEach {
            //height
            val fHei = (it.value.toFloat() / maxVal!!) * (measuredHeight - 30)
            listHeightInc.add(fHei)
            //width
            listWidthInterval.add(startX)
            startX += intervalX
        }
        createPaints()
        invalidate()
        createAnimators()
    }

    fun createAnimators() {
        animatorSet = AnimatorSet()
        var aniBuilder: AnimatorSet.Builder? = null
        listHeightInc.forEachIndexed { index, fl ->
            val anim = ValueAnimator.ofFloat(0f, fl)
            anim.interpolator = AnticipateOvershootInterpolator()
            anim.addUpdateListener {
                listHeightInc[index] = it.animatedValue as Float
                invalidate()
            }
            if (index == 0)
                aniBuilder = animatorSet?.play(anim)
            else
                aniBuilder?.with(anim)
        }
        animatorSet?.duration = 1500
        animatorSet?.start()
    }

    fun createPaints() {
        listHeightInc.forEachIndexed { index, _ ->
            val linePaint = Paint()
            linePaint.isAntiAlias = true
            linePaint.color = Color.parseColor(mPaintColors[index])
            linePaint.style = Paint.Style.STROKE
            linePaint.strokeCap = Paint.Cap.SQUARE
            linePaint.strokeWidth = 6F
            mPaintLine.add(linePaint)
            //
            val textPaint = Paint()
            textPaint.isAntiAlias = true
            textPaint.color = Color.parseColor(mPaintColors[index])
            textPaint.textSize = 23f
            textPaint.textAlign = Paint.Align.LEFT
            mPaintText.add(textPaint)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //
        listHeightInc.forEachIndexed { index, fl ->
            canvas?.drawLine(listWidthInterval[index], (measuredHeight - 30).toFloat(), listWidthInterval[index], (measuredHeight - 30).toFloat() - fl, mPaintLine[index])
            canvas?.drawText(mAllMapData.keys.toMutableList()[index], listWidthInterval[index], measuredHeight.toFloat(), mPaintText[index])
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }
}