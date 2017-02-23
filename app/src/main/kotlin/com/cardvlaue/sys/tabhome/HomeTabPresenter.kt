package com.cardvlaue.sys.tabhome

import android.content.Context
import android.net.Uri
import com.cardvlaue.sys.R
import com.cardvlaue.sys.data.source.TasksRepository
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import javax.inject.Inject

class HomeTabPresenter @Inject constructor(val tasksRepository: TasksRepository, val mView: HomeTabContract.View, val mContext: Context) : HomeTabContract.Presenter {

    private val url_t: String = "" // http://image72.360doc.com/DownloadImg/2014/05/2605/42035151_6.jpg

    override fun start() {
        mView.startLoopPager()

        mView.setAdImage(url_t)

        val data = arrayListOf<HomeLooperAdapterBean>()
        for (i in 1..HomeTabFragment.SIZE_VIEW_PAGER) {
            data.add(HomeLooperAdapterBean(createAdView(mContext, url_t)))
        }
        mView.setPagerData(data)
    }

    private fun createAdView(context: Context, imgUrl: String) : SimpleDraweeView {
        val slideshowView = SimpleDraweeView(context)
        val hierarchy = GenericDraweeHierarchyBuilder(context.resources)
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImage(R.mipmap.img_home_slideshow)
                .build()
        slideshowView.hierarchy = hierarchy
        slideshowView.setImageURI(Uri.parse(imgUrl), context)
        return slideshowView
    }

}