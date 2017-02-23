package com.cardvlaue.sys.tabapply

import com.cardvlaue.sys.data.source.TasksRepositoryComponent
import com.cardvlaue.sys.util.FragmentScoped
import dagger.Component

@FragmentScoped
@Component(dependencies = arrayOf(TasksRepositoryComponent::class), modules = arrayOf(ApplyTabPresenterModule::class))
interface ApplyTabComponent {

    fun inject(applyTabFragment: ApplyTabFragment)

}