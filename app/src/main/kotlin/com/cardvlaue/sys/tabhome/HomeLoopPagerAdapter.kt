package com.cardvlaue.sys.tabhome

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

class HomeLoopPagerAdapter : PagerAdapter() {

    val mData = arrayListOf<HomeLooperAdapterBean>()

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view == `object`

    override fun getCount(): Int = mData.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) = container.removeView(`object` as View)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mData[position].simpleDraweeView
        container.addView(view)
        return view
    }

    fun updateData(list: List<HomeLooperAdapterBean>) {
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }

}