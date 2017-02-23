package com.cardvlaue.sys.tabhome

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class HomeTabPresenterModule(val view: HomeTabContract.View, val c: Context) {

    @Provides
    fun providesMoreTabContractView() : HomeTabContract.View = view

    @Provides
    fun providesContext() : Context = c

}