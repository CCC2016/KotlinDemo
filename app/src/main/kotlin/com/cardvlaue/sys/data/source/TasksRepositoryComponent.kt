package com.cardvlaue.sys.data.source

import com.cardvlaue.sys.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * This is a Dagger component. Refer to {@link MyApplication} for the list of Dagger components
 * used in this application.
 * <P>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * MyApplication}.
 */
@Singleton
@Component(modules = arrayOf(TasksRepositoryModule::class, ApplicationModule::class))
interface TasksRepositoryComponent {

    fun getTasksRepository() : TasksRepository
}