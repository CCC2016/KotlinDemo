package com.cardvlaue.sys.about

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AboutPresenterModule(val view: AboutContract.View, val c: Context) {

    @Provides
    fun providesAboutContractView() = view

    @Provides
    fun providesContext() = c

}