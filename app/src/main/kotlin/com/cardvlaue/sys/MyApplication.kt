package com.cardvlaue.sys

import android.support.multidex.MultiDexApplication
import com.cardvlaue.sys.data.source.DaggerTasksRepositoryComponent
import com.cardvlaue.sys.data.source.TasksRepositoryComponent
import com.cardvlaue.sys.data.source.TasksRepositoryModule
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import okhttp3.OkHttpClient
import timber.log.Timber

/**
 * Even though Dagger2 allows annotating a {@link dagger.Component} as a singleton, the code itself
 * must ensure only one instance of the class is created. Therefore, we create a custom
 * {@link Application} class to store a singleton reference to the {@link
 * TasksRepositoryComponent}.
 */
class MyApplication : MultiDexApplication() {

    private lateinit var mRepositoryComponent: TasksRepositoryComponent

    override fun onCreate() {
        super.onCreate()

        mRepositoryComponent = DaggerTasksRepositoryComponent.builder().applicationModule(ApplicationModule(applicationContext)).tasksRepositoryModule(TasksRepositoryModule()).build()

        Fresco.initialize(applicationContext, OkHttpImagePipelineConfigFactory.newBuilder(applicationContext, OkHttpClient()).build())

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    fun getTasksRepositoryComponent() : TasksRepositoryComponent {
        return mRepositoryComponent
    }

}