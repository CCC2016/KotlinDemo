package com.cardvlaue.sys.util

import android.content.Context
import android.content.pm.PackageManager

object DeviceUtils {

    /**
     * 版本名
     */
    fun versionName(context: Context) : String = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES).versionName
}