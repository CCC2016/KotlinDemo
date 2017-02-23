package com.cardvlaue.sys.tabmy

import dagger.Module
import dagger.Provides

@Module
class MyTabPresenterModule(val view: MyTabContract.View) {

    @Provides
    fun providesMyTabContractView() = view

}