package com.cardvlaue.sys.financeintent

import com.cardvlaue.sys.BasePresenter
import com.cardvlaue.sys.BaseView

interface FinanceIntentContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter

}