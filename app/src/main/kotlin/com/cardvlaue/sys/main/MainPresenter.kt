package com.cardvlaue.sys.main

import com.cardvlaue.sys.data.source.TasksRepository
import javax.inject.Inject

class MainPresenter @Inject constructor(val tasksRepository: TasksRepository, val mView: MainContract.View) : MainContract.Presenter {

    @Inject
    fun setupListeners() {
        mView.setPresenter(this)
    }

    override fun start() {
        mView.setSplashImage("")
        mView.goneSplashView()
    }

}