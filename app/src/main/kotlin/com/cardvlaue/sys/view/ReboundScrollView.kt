package com.cardvlaue.sys.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ScrollView

class ReboundScrollView : ScrollView {

    private var mChildView: View? = null

    /* private var mListener: ReboundScrollListener? = null */

    private val mOriginalRect = Rect()

    private var canPullDown: Boolean = false

    private var canPullUp: Boolean = false

    private var haveMoved: Boolean = false

    private var startY: Float = 0f

    private var changeY: Float = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) mChildView = getChildAt(0)
    }

    override fun fling(velocityY: Int) {
        super.fling(velocityY / 2)
    }

    /* override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (mListener != null) {
            mListener!!.onScrolled(-1, scrollY.toFloat())
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    } */

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mChildView != null) mOriginalRect.set(mChildView!!.left, mChildView!!.top, mChildView!!.right, mChildView!!.bottom)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (mChildView != null) {
            val action = ev.action

            when(action) {
                MotionEvent.ACTION_DOWN -> {
                    canPullDown = isCanPullDown()
                    canPullUp = isCanPullUp()
                    startY = ev.y
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    if (haveMoved) {
                        val anim = TranslateAnimation(0f, 0f, mChildView!!.top.toFloat(), mOriginalRect.top.toFloat())
                        anim.duration = ANIM_TIME.toLong()
                        mChildView!!.startAnimation(anim)

                        // 将标志位设回 false
                        canPullDown = false
                        canPullUp = false
                        haveMoved = false
                        resetViewLayout()

                        /* if (mListener != null) mListener!!.onScrolled(action, changeY) */
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (!canPullDown && !canPullUp) {
                        canPullDown = isCanPullDown()
                        canPullUp = isCanPullUp()
                        startY = ev.y
                    } else {
                        changeY = ev.y - startY
                        if ((canPullDown && changeY > 0) || (canPullUp && changeY < 0) || canPullUp && canPullDown) {
                            val offset = (changeY * MOVE_DELAY).toInt()
                            mChildView!!.layout(mOriginalRect.left, mOriginalRect.top + offset, mOriginalRect.right, mOriginalRect.bottom + offset)

                            haveMoved = true
                        }
                    }
                }

                else -> {  }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    /* fun setScrollListener(l: ReboundScrollListener) {
        mListener = l
    }

    interface ReboundScrollListener {
        fun onScrolled(action: Int, y: Float)
    } */

    /**
     * 判断是否滚动到顶部
     */
    private fun isCanPullDown() : Boolean = scrollY == 0 || mChildView!!.height < height + scrollY

    /**
     * 判断是否滚动到底部
     */
    private fun isCanPullUp() : Boolean = mChildView!!.height <= height + scrollY

    private fun resetViewLayout() = mChildView!!.layout(mOriginalRect.left, mOriginalRect.top, mOriginalRect.right, mOriginalRect.bottom)

    companion object {
        private val ANIM_TIME = 300
        private val MOVE_DELAY = 0.3f
    }
}