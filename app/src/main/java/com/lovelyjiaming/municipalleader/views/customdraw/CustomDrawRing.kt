package com.lovelyjiaming.municipalleader.views.customdraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CustomDrawRing constructor(private val ctx: Context, val attr: AttributeSet) : View(ctx, attr) {
    private val mFirstPaint: Paint
    private val mThirdPaint: Paint
    private val mOuterCirclePaint: Paint

    init {
        mOuterCirclePaint = Paint()
        mOuterCirclePaint.isAntiAlias = true
        mOuterCirclePaint.color = Color.LTGRAY
        mOuterCirclePaint.style = Paint.Style.STROKE
        mOuterCirclePaint.strokeWidth = 1f
        mOuterCirclePaint.strokeCap = Paint.Cap.SQUARE

        //未完成
        mFirstPaint = Paint()
        mFirstPaint.isAntiAlias = true
        mFirstPaint.color = Color.parseColor("#87CEFA")
        mFirstPaint.style = Paint.Style.STROKE
        mFirstPaint.strokeCap = Paint.Cap.SQUARE
        mFirstPaint.strokeWidth = 20F

        //已完成
        mThirdPaint = Paint()
        mThirdPaint.isAntiAlias = true
        mThirdPaint.color = Color.parseColor("#9F79EE")
        mThirdPaint.style = Paint.Style.STROKE
        mThirdPaint.strokeCap = Paint.Cap.SQUARE
        mThirdPaint.strokeWidth = 30F
    }

    private var mFinished: Float = 0f
    private var mUnFinished: Float = 0f
    //根据塞入的值，计算比例
    fun setData(finished: Int, unfinished: Int) {
        mFinished = finished.toFloat()
        mUnFinished = unfinished.toFloat()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //
        if (mFinished + mUnFinished == 0f) return
        val oval = RectF(40f, 40f, measuredWidth.toFloat() - 40, measuredHeight.toFloat() - 40)
        canvas?.apply {
            val fAngle = (mFinished / (mFinished + mUnFinished)) * 360f
            val fUnAngle = (mUnFinished / (mFinished + mUnFinished)) * 360f
            drawArc(oval, 0f, fAngle, false, mThirdPaint)
            drawArc(oval, fAngle, fUnAngle, false, mFirstPaint)
            drawCircle(measuredWidth / 2f, measuredWidth / 2f, measuredWidth / 2 - 10f, mOuterCirclePaint)
        }
    }
}