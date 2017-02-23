package com.cardvlaue.sys.main

import dagger.Module
import dagger.Provides

@Module
class MainPresenterModule(val view: MainContract.View) {

    @Provides
    fun providesMainContractView() : MainContract.View = view

}