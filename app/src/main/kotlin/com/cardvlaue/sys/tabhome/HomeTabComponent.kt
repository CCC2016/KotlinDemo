package com.cardvlaue.sys.tabhome

import com.cardvlaue.sys.data.source.TasksRepositoryComponent
import com.cardvlaue.sys.util.FragmentScoped
import dagger.Component

@FragmentScoped
@Component(dependencies = arrayOf(TasksRepositoryComponent::class), modules = arrayOf(HomeTabPresenterModule::class))
interface HomeTabComponent {

    fun inject(homeTabFragment: HomeTabFragment)

}