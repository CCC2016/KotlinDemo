package com.cardvlaue.sys.tabmore

import com.cardvlaue.sys.BasePresenter
import com.cardvlaue.sys.BaseView

interface MoreTabContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {

        /**
         * 服务电话
         */
        fun clickPhone()

        /**
         * 融资攻略
         */
        fun clickStrategy()

        /**
         * 公司简介
         */
        fun clickIntroduction()

        /**
         * 资质荣誉
         */
        fun clickHonour()

        /**
         * 联系方式
         */
        fun clickContact()

        /**
         * 关于我们
         */
        fun clickAbout()

        /**
         * 意见反馈
         */
        fun clickFeedback()

        /**
         * 在线客服
         */
        fun clickService()
    }

}