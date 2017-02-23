package com.cardvlaue.sys.tabmy

import com.cardvlaue.sys.data.source.TasksRepository
import javax.inject.Inject

class MyTabPresenter @Inject constructor(val tasksRepository: TasksRepository, val view: MyTabContract.View) : MyTabContract.Presenter {

    override fun start() {
        view.setShopNumber("0")
        view.setGiftNumber("0")
        view.setInviteNumber("0")
        view.setGiftSurplus("剩余0个")
        view.setFriendSurplus("已成功推荐0个")
    }
}