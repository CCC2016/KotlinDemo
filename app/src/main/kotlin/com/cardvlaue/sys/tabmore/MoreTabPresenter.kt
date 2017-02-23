package com.cardvlaue.sys.tabmore

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import com.cardvlaue.sys.about.AboutActivity
import com.cardvlaue.sys.util.IntentUtils
import com.cardvlaue.sys.util.UrlConstants
import com.cardvlaue.sys.util.WebLoadFragment
import javax.inject.Inject

class MoreTabPresenter @Inject constructor(val view: MoreTabContract.View, val mContext: Context, val mFM: FragmentManager) : MoreTabContract.Presenter {

    override fun clickPhone() {
        AlertDialog.Builder(mContext).setMessage("拨打客服电话").setPositiveButton("呼叫") { dialogInterface, i ->
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:4008-803-803"))
            if (IntentUtils.isCallPhoneIntentSafe(mContext, intent))
                mContext.startActivity(intent)
        }.setNegativeButton("取消", null).show()
    }

    override fun clickStrategy() {
        WebLoadFragment.newInstance(MoreTabFragment.STR_STRATEGY, UrlConstants.STRATEGY).show(mFM, MoreTabFragment.STR_STRATEGY)
    }

    override fun clickIntroduction() {
        WebLoadFragment.newInstance(MoreTabFragment.STR_INTRODUCTION, UrlConstants.INTRODUCTION).show(mFM, MoreTabFragment.STR_INTRODUCTION)
    }

    override fun clickHonour() {
        WebLoadFragment.newInstance(MoreTabFragment.STR_HONOUR, UrlConstants.HONOUR).show(mFM, MoreTabFragment.STR_HONOUR)
    }

    override fun clickContact() {
        WebLoadFragment.newInstance(MoreTabFragment.STR_CONTRACT, UrlConstants.CONTACT).show(mFM, MoreTabFragment.STR_CONTRACT)
    }

    override fun clickAbout() {
        mContext.startActivity(Intent(mContext, AboutActivity::class.java))
    }

    override fun clickFeedback() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickService() {
        MQServiceDialogFragment().show(mFM, "MQServiceDialogFragment")
    }

    override fun start() {  }

}