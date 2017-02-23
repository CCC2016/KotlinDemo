package com.cardvlaue.sys.about

import com.cardvlaue.sys.util.FragmentScoped
import dagger.Component

@FragmentScoped
@Component(modules = arrayOf(AboutPresenterModule::class))
interface AboutComponent {

    fun inject(aboutActivity: AboutActivity)

}