package com.lovelyjiaming.municipalleader.views.customdraw

import android.animation.Animator
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
    private val mThirdPaint: Paint
    private val mOuterCirclePaint: Paint
    //
    private var mFinished: Float = 0f
    private var mUnFinished: Float = 0f
    var mUpdateValueF: Float = 0f
    var mUpdateValueUF: Float = 0f

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
        mFirstPaint.color = Color.parseColor("#af87CEFA")
        mFirstPaint.style = Paint.Style.STROKE
        mFirstPaint.strokeCap = Paint.Cap.SQUARE
        mFirstPaint.strokeWidth = 20F

        //已完成
        mThirdPaint = Paint()
        mThirdPaint.isAntiAlias = true
        mThirdPaint.color = Color.parseColor("#af9F79EE")
        mThirdPaint.style = Paint.Style.STROKE
        mThirdPaint.strokeCap = Paint.Cap.SQUARE
        mThirdPaint.strokeWidth = 30F
    }

    //根据塞入的值，计算比例
    fun setData(finished: Int, unfinished: Int) {
        mUpdateValueF = 0f
        mUpdateValueUF = 0f
        invalidate()
        mFinished = finished.toFloat()
        mUnFinished = unfinished.toFloat()
        //
        val tmp1 = (mFinished / (mFinished + mUnFinished)) * 360f
        val animate1 = ValueAnimator.ofFloat(0f, tmp1)
        animate1.duration = 900
        animate1.interpolator = AnticipateOvershootInterpolator()
        animate1.addUpdateListener {
            mUpdateValueF = it.animatedValue as Float
            invalidate()
        }
        animate1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                //
                val tmp2 = (mUnFinished / (mFinished + mUnFinished)) * 360f
                val animate2 = ValueAnimator.ofFloat(0f, tmp2)
                animate2.duration = 900
                animate2.interpolator = AnticipateOvershootInterpolator()
                animate2.addUpdateListener {
                    mUpdateValueUF = it.animatedValue as Float
                    invalidate()
                }
                animate2.start()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
        animate1.start()
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
            drawArc(oval, 0f, mUpdateValueF, false, mThirdPaint)
            drawArc(oval, mUpdateValueF, mUpdateValueUF, false, mFirstPaint)
            drawCircle(measuredWidth / 2f, measuredWidth / 2f, measuredWidth / 2 - 10f, mOuterCirclePaint)
        }
    }
}