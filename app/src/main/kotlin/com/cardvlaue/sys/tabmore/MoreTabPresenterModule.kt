package com.cardvlaue.sys.tabmore

import android.content.Context
import android.support.v4.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class MoreTabPresenterModule(val view: MoreTabContract.View, val fm: FragmentManager, val c: Context) {

    @Provides
    fun providesMoreTabContractView() : MoreTabContract.View = view

    @Provides
    fun providesFragmentManager() : FragmentManager = fm

    @Provides
    fun providesContext() : Context = c

}