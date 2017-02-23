package com.cardvlaue.sys.about

import com.cardvlaue.sys.BasePresenter
import com.cardvlaue.sys.BaseView

interface AboutContract {

    interface View : BaseView<Presenter> {

        fun setCacheText(string: String)
    }

    interface Presenter : BasePresenter {

        fun clickClearCache()
    }

}