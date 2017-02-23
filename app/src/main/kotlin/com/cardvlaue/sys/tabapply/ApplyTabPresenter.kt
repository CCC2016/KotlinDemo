package com.cardvlaue.sys.tabapply

import javax.inject.Inject

class ApplyTabPresenter @Inject constructor(val mView: ApplyTabContract.View) : ApplyTabContract.Presenter {

    override fun start() {
        val data = arrayListOf<ApplyListsItemBean>()
        for (i in 1..10) {
            val item = ApplyListsItemBean()
            item.time = "申请提交时间    2016-0$i-28"
            item.name = "四川香天下湘菜馆"
            item.limit = "融资额度：${i}000元"
            item.term = "期望融资期限：三个月"
            data.add(item)
        }
        mView.updateAdapter(data)
    }

}