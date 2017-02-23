package com.cardvlaue.sys.tabmy

import com.cardvlaue.sys.BasePresenter
import com.cardvlaue.sys.BaseView

interface MyTabContract {

    interface View : BaseView<Presenter> {
        /**
         * 我的店铺
         */
        fun setShopNumber(string: String)

        /**
         * 我的红包
         */
        fun setGiftNumber(string: String)

        /**
         * 我的邀请
         */
        fun setInviteNumber(string: String)

        /**
         * 我的剩余红包
         */
        fun setGiftSurplus(string: String)

        /**
         * 邀请好友赢红包
         */
        fun setFriendSurplus(string: String)
    }

    interface Presenter : BasePresenter

}