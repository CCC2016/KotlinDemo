package com.cardvlaue.sys.financeintent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cardvlaue.sys.util.ActivityUtils
import org.jetbrains.anko.frameLayout

/**
 * 融资意向
 */
class FinanceIntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            id = CONTENT_FRAME
        }

        var fragment = supportFragmentManager.findFragmentById(CONTENT_FRAME)

        if (fragment == null) {
            fragment = FinanceIntentFragment()

            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, CONTENT_FRAME)
        }
    }

    companion object {
        private val CONTENT_FRAME = 1
    }

}