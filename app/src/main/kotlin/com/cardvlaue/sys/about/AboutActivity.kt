package com.cardvlaue.sys.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cardvlaue.sys.util.ActivityUtils
import org.jetbrains.anko.frameLayout
import javax.inject.Inject

/**
 * 关于我们
 */
class AboutActivity :AppCompatActivity() {

    @Inject
    lateinit var aboutPresenter: AboutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            id = CONTENT_FRAME
        }

        var fragment = supportFragmentManager.findFragmentById(CONTENT_FRAME)

        if (fragment == null) {
            fragment = AboutFragment.newInstance()

            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, CONTENT_FRAME)
        }

        DaggerAboutComponent.builder().aboutPresenterModule(AboutPresenterModule(fragment as AboutFragment, this)).build().inject(this)
    }

    companion object {

        private val CONTENT_FRAME = 1
    }

}