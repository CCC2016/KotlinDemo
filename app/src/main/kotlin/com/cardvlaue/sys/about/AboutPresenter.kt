package com.cardvlaue.sys.about

import android.content.Context
import android.support.v7.app.AlertDialog
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AboutPresenter @Inject constructor(val view: AboutContract.View) : AboutContract.Presenter {

    private lateinit var context: Context

    override fun clickClearCache() {
        AlertDialog.Builder(context).setMessage("清除缓存").setPositiveButton("清除") { dialogInterface, i ->
            Fresco.getImagePipelineFactory().mainFileCache.trimToMinimum()
            Fresco.getImagePipeline().clearCaches()
            view.setCacheText("0 B")
        }.setNegativeButton("取消", null).show()
    }

    @Inject
    fun setupListeners() {
        view.setPresenter(this)
    }

    @Inject
    fun setupContext(c: Context) {
        context = c
    }

    override fun start() {
        Fresco.getImagePipelineFactory().mainFileCache.trimToMinimum()
        val cacheSize = Fresco.getImagePipelineFactory().mainFileCache.size
        Timber.d("Fresco Size:" + cacheSize)
        view.setCacheText(humanReadableByteCount(cacheSize, false))
    }

    fun humanReadableByteCount(bytes: Long, si: Boolean) : String {
        val unit = if (si) 1000 else 1024
        if (bytes < unit) return "$bytes B"
        val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
        val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1] + if (si) "i" else ""
        return String.format(Locale.getDefault(), "%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
    }
}