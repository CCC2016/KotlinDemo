package com.cardvlaue.sys.main

import com.cardvlaue.sys.data.source.TasksRepositoryComponent
import com.cardvlaue.sys.util.FragmentScoped
import dagger.Component

@FragmentScoped
@Component(dependencies = arrayOf(TasksRepositoryComponent::class), modules = arrayOf(MainPresenterModule::class))
interface MainComponent {

    fun inject(mainActivity: MainActivity)

}