package com.lovelyjiaming.municipalleader.views.customdraw

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
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

    init {
        mFirstStatusPaint = Paint()
        mFirstStatusPaint.isAntiAlias = true
        mFirstStatusPaint.style = Paint.Style.FILL
        mFirstStatusPaint.strokeCap = Paint.Cap.SQUARE
        mFirstStatusPaint.color = Color.parseColor("#DB394A")

        //
        mSecondStatusPaint = Paint()
        mSecondStatusPaint.isAntiAlias = true
        mSecondStatusPaint.style = Paint.Style.FILL
        mSecondStatusPaint.strokeCap = Paint.Cap.SQUARE
        mSecondStatusPaint.color = Color.parseColor("#9400D3")
        //
        mThirdStatusPaint = Paint()
        mThirdStatusPaint.isAntiAlias = true
        mThirdStatusPaint.style = Paint.Style.FILL
        mThirdStatusPaint.strokeCap = Paint.Cap.SQUARE
        mThirdStatusPaint.color = Color.parseColor("#97FFFF")
        //
        mFourthStatusPaint = Paint()
        mFourthStatusPaint.isAntiAlias = true
        mFourthStatusPaint.style = Paint.Style.FILL
        mFourthStatusPaint.strokeCap = Paint.Cap.SQUARE
        mFourthStatusPaint.color = Color.parseColor("#90EE90")
        //
        mFifthStatusPaint = Paint()
        mFifthStatusPaint.isAntiAlias = true
        mFifthStatusPaint.style = Paint.Style.FILL
        mFifthStatusPaint.strokeCap = Paint.Cap.SQUARE
        mFifthStatusPaint.color = Color.parseColor("#CDCD00")
    }

    fun startAnimateDraw() {
        val animate = ValueAnimator.ofFloat(0f, 72f)
        animate.duration = 1500
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
        canvas?.drawArc(oval, 0f, mUpdateValue, true, mFirstStatusPaint)
        canvas?.drawArc(oval, 72f, mUpdateValue, true, mSecondStatusPaint)
        canvas?.drawArc(oval, 144f, mUpdateValue, true, mThirdStatusPaint)
        canvas?.drawArc(oval, 216f, mUpdateValue, true, mFourthStatusPaint)
        canvas?.drawArc(oval, 288f, mUpdateValue, true, mFifthStatusPaint)
    }
}