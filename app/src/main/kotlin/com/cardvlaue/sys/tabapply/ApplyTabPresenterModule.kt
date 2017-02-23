package com.cardvlaue.sys.tabapply

import dagger.Module
import dagger.Provides

@Module
class ApplyTabPresenterModule(val view: ApplyTabContract.View) {

    @Provides
    fun providesApplyTabContractView() : ApplyTabContract.View = view

}