package com.cardvlaue.sys.main

import com.cardvlaue.sys.BasePresenter
import com.cardvlaue.sys.BaseView

interface MainContract {

    interface View : BaseView<Presenter> {

        /**
         * 设置启动页图片
         */
        fun setSplashImage(uriStr: String)

        /**
         * 隐藏启动页
         */
        fun goneSplashView()
    }

    interface Presenter : BasePresenter

}