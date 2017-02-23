package com.cardvlaue.sys.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.view.MotionEvent
import android.view.ViewConfiguration

/**
 * Created by AItsuki on 2016/1/20.
 */
class VpSwipeRefreshLayout(context: Context?) : SwipeRefreshLayout(context) {

    private val mTouchSlop: Int

    private var startX: Float = 0f
    private var startY: Float = 0f

    private var mIsVpDragger: Boolean = false

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startY = ev.y
                startX = ev.x
                mIsVpDragger = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (mIsVpDragger) {
                    return false
                }

                val distanceX = Math.abs(ev.x - startX)
                val distanceY = Math.abs(ev.y - startY)

                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mIsVpDragger = false
        }

        return super.onInterceptTouchEvent(ev)
    }

}