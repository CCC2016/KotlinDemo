package com.cardvlaue.sys.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.view.View
import com.cardvlaue.sys.R
import org.jetbrains.anko.dip
import timber.log.Timber

/**
 * 加号
 */
class AddView(mContext: Context?) : View(mContext) {

    val mPaint: Paint

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = ContextCompat.getColor(context, R.color.app_main_color)
        mPaint.strokeWidth = dip(1).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val halfHeight = (height / 2).toFloat()
        val halfWidth = (height / 2).toFloat()
        Timber.e("half:%s|||%s", halfWidth, halfHeight)

        canvas.drawLine(halfWidth, 0f, halfWidth, height.toFloat(), mPaint)
        canvas.drawLine(0f, halfHeight, width.toFloat(), halfHeight, mPaint)
    }

}