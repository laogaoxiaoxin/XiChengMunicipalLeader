package com.lovelyjiaming.municipalleader.views.customdraw

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator

class CustomDrawCirclePie(private val ctx: Context, val attr: AttributeSet) : View(ctx, attr) {
    private var mUpdateValue = 0f
    private var mFirstStatusPaint: Paint
    private var mSecondStatusPaint: Paint
    private var mThirdStatusPaint: Paint
    private var mFourthStatusPaint: Paint
    private var mFifthStatusPaint: Paint
    //外圈
    private var mOuterCirclePaint: Paint

    init {
        mFirstStatusPaint = Paint()
        mFirstStatusPaint.isAntiAlias = true
        mFirstStatusPaint.style = Paint.Style.STROKE
        mFirstStatusPaint.strokeCap = Paint.Cap.SQUARE
        mFirstStatusPaint.strokeWidth = 25f
        mFirstStatusPaint.color = Color.parseColor("#9fDB394A")

        //
        mSecondStatusPaint = Paint()
        mSecondStatusPaint.strokeWidth = 25f
        mSecondStatusPaint.isAntiAlias = true
        mSecondStatusPaint.style = Paint.Style.STROKE
        mSecondStatusPaint.strokeCap = Paint.Cap.SQUARE
        mSecondStatusPaint.color = Color.parseColor("#9f9400D3")
        //
        mThirdStatusPaint = Paint()
        mThirdStatusPaint.isAntiAlias = true
        mThirdStatusPaint.style = Paint.Style.STROKE
        mThirdStatusPaint.strokeWidth = 25f
        mThirdStatusPaint.strokeCap = Paint.Cap.SQUARE
        mThirdStatusPaint.color = Color.parseColor("#9f97FFFF")
        //
        mFourthStatusPaint = Paint()
        mFourthStatusPaint.isAntiAlias = true
        mFourthStatusPaint.strokeWidth = 25f
        mFourthStatusPaint.style = Paint.Style.STROKE
        mFourthStatusPaint.strokeCap = Paint.Cap.SQUARE
        mFourthStatusPaint.color = Color.parseColor("#9f90EE90")
        //
        mFifthStatusPaint = Paint()
        mFifthStatusPaint.strokeWidth = 25f
        mFifthStatusPaint.isAntiAlias = true
        mFifthStatusPaint.style = Paint.Style.STROKE
        mFifthStatusPaint.strokeCap = Paint.Cap.SQUARE
        mFifthStatusPaint.color = Color.parseColor("#9fCDCD00")

        //
        mOuterCirclePaint = Paint()
        mOuterCirclePaint.strokeWidth = 1f
        mOuterCirclePaint.isAntiAlias = true
        mOuterCirclePaint.color = Color.parseColor("#a2a2a2")
        mOuterCirclePaint.style = Paint.Style.STROKE
        mOuterCirclePaint.strokeCap = Paint.Cap.SQUARE
    }

    fun startAnimateDraw() {
        val animate = ValueAnimator.ofFloat(0f, 72f)
        animate.duration = 1000
        animate.interpolator = AnticipateOvershootInterpolator()
        animate.addUpdateListener {
            mUpdateValue = it.animatedValue as Float
            invalidate()
        }
        animate.start()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val oval = RectF(40f, 40f, measuredWidth - 40f, measuredWidth - 40f)

        canvas?.apply {
            drawArc(oval, 0f, mUpdateValue, false, mFirstStatusPaint)
            drawArc(oval, 72f, mUpdateValue, false, mSecondStatusPaint)
            drawArc(oval, 144f, mUpdateValue, false, mThirdStatusPaint)
            drawArc(oval, 216f, mUpdateValue, false, mFourthStatusPaint)
            drawArc(oval, 288f, mUpdateValue, false, mFifthStatusPaint)
            drawCircle(measuredWidth / 2f, measuredWidth / 2f, measuredWidth / 2f - 5f, mOuterCirclePaint)
        }

    }
}