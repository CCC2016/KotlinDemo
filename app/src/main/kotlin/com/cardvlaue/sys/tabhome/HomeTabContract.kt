package com.cardvlaue.sys.tabhome

import com.cardvlaue.sys.BasePresenter
import com.cardvlaue.sys.BaseView
import com.cardvlaue.sys.tabmore.MoreTabContract

interface HomeTabContract {

    interface View : BaseView<MoreTabContract.Presenter> {

        /**
         * 开启自动轮播图
         */
        fun startLoopPager()

        /**
         * 设置广告图
         */
        fun setAdImage(urlStr: String)

        /**
         * 设置轮播图数据
         */
        fun setPagerData(list: List<HomeLooperAdapterBean>)
    }

    interface Presenter : BasePresenter

}