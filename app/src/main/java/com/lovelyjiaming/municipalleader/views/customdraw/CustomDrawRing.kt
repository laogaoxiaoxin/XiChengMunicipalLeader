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

class CustomDrawRing constructor(private val ctx: Context, val attr: AttributeSet) : View(ctx, attr) {
    private val mFirstPaint: Paint
    private val mSecondPaint: Paint
    private val mThirdPaint: Paint
    var mCurrentAngleLength: Float = 0f
    val mOuterCirclePaint: Paint

    init {
        mOuterCirclePaint = Paint()
        mOuterCirclePaint.isAntiAlias = true
        mOuterCirclePaint.color = Color.LTGRAY
        mOuterCirclePaint.style = Paint.Style.STROKE
        mOuterCirclePaint.strokeWidth = 1f
        mOuterCirclePaint.strokeCap = Paint.Cap.SQUARE

        //
        mFirstPaint = Paint()
        mFirstPaint.isAntiAlias = true
        mFirstPaint.color = Color.parseColor("#87CEFA")
        mFirstPaint.style = Paint.Style.STROKE
        mFirstPaint.strokeCap = Paint.Cap.SQUARE
        mFirstPaint.strokeWidth = 20F
        //
        mSecondPaint = Paint()
        mSecondPaint.isAntiAlias = true
        mSecondPaint.color = Color.parseColor("#8DEEEE")
        mSecondPaint.style = Paint.Style.STROKE
        mSecondPaint.strokeCap = Paint.Cap.SQUARE
        mSecondPaint.strokeWidth = 20F

        //
        mThirdPaint = Paint()
        mThirdPaint.isAntiAlias = true
        mThirdPaint.color = Color.parseColor("#9F79EE")
        mThirdPaint.style = Paint.Style.STROKE
        mThirdPaint.strokeCap = Paint.Cap.SQUARE
        mThirdPaint.strokeWidth = 30F

        startRingAnimation()
    }

    private fun startRingAnimation() {
        val progressAnimator = ValueAnimator.ofFloat(0f, 120f)
        progressAnimator.duration = 1000
        progressAnimator.interpolator = AnticipateOvershootInterpolator()
        progressAnimator.setTarget(mCurrentAngleLength)
        progressAnimator.addUpdateListener { animation ->
            mCurrentAngleLength = animation.animatedValue as Float
            invalidate()
        }
        progressAnimator.start()
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
        val oval = RectF(40f, 40f, measuredWidth.toFloat() - 40, measuredHeight.toFloat() - 40)
        canvas?.drawArc(oval, 0f, mCurrentAngleLength, false, mFirstPaint)
        canvas?.drawArc(oval, 120f, mCurrentAngleLength, false, mSecondPaint)
        canvas?.drawArc(oval, 240f, mCurrentAngleLength, false, mThirdPaint)
        canvas?.drawCircle(measuredWidth / 2f, measuredWidth / 2f, measuredWidth / 2 - 10f, mOuterCirclePaint)
    }
}