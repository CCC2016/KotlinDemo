package com.cardvlaue.sys.tabmy

import com.cardvlaue.sys.data.source.TasksRepositoryComponent
import com.cardvlaue.sys.util.FragmentScoped
import dagger.Component

@FragmentScoped
@Component(dependencies = arrayOf(TasksRepositoryComponent::class), modules = arrayOf(MyTabPresenterModule::class))
interface MyTabComponent {

    fun inject(myTabFragment: MyTabFragment)

}