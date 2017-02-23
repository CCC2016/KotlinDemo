package com.cardvlaue.sys.tabmore

import com.cardvlaue.sys.util.FragmentScoped
import dagger.Component

@FragmentScoped
@Component(modules = arrayOf(MoreTabPresenterModule::class))
interface MoreTabComponent {

    fun inject(moreTabFragment: MoreTabFragment)

}