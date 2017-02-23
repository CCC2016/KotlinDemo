package com.cardvlaue.sys.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cardvlaue.sys.MyApplication
import com.cardvlaue.sys.util.ActivityUtils
import org.jetbrains.anko.frameLayout
import javax.inject.Inject

/**
 * 主界面
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 布局
         */
        frameLayout {
            id = CONTENT_FRAME
        }

        var fragment = supportFragmentManager.findFragmentById(CONTENT_FRAME)

        if (fragment == null) {
            fragment = MainFragment.newInstance()

            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, CONTENT_FRAME)
        }

        DaggerMainComponent.builder().mainPresenterModule(MainPresenterModule(fragment as MainFragment))
                .tasksRepositoryComponent((application as MyApplication).getTasksRepositoryComponent()).build().inject(this)
    }

    companion object {
        private val CONTENT_FRAME = 1
    }

}