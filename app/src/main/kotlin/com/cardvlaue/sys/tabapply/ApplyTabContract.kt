package com.cardvlaue.sys.tabapply

import com.cardvlaue.sys.BasePresenter
import com.cardvlaue.sys.BaseView

interface ApplyTabContract {

    interface View : BaseView<Presenter> {

        fun updateAdapter(list: List<ApplyListsItemBean>)
    }

    interface Presenter : BasePresenter

}