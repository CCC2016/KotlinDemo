package com.cardvlaue.sys.tabmore

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.contentLoadingProgressBar

/**
 * 美洽
 */
class MQServiceDialogFragment :DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            contentLoadingProgressBar {

            }
        }.view
    }
}