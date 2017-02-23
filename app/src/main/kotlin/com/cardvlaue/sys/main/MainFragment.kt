package com.cardvlaue.sys.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentTabHost
import android.support.v7.content.res.AppCompatResources
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import com.cardvlaue.sys.R
import com.cardvlaue.sys.tabapply.ApplyTabFragment
import com.cardvlaue.sys.tabhome.HomeTabFragment
import com.cardvlaue.sys.tabmore.MoreTabFragment
import com.cardvlaue.sys.tabmy.MyTabFragment
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.fragmentTabHost
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainFragment :  RxFragment(), MainContract.View {

    private lateinit var mPresenter: MainContract.Presenter

    private lateinit var mImageView: SimpleDraweeView

    private lateinit var mSplashView: _FrameLayout

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mImageView = SimpleDraweeView(activity)
        mImageView.hierarchy = GenericDraweeHierarchyBuilder(resources)
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImage(R.mipmap.bg_splash)
                .build()
        mImageView.setOnTouchListener { view, motionEvent -> true }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            frameLayout {

                /**
                 * 真-首页
                 */
                fragmentTabHost {
                    id = android.R.id.tabhost

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        frameLayout {
                            id = android.R.id.tabcontent
                        }.lparams {
                            height = dip(0)
                            weight = 1f
                        }

                        /**
                         * 启动页
                         */
                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            view {
                                backgroundColor = Color.parseColor("#E0E0E0")
                            }.lparams {
                                height = 1
                            }

                            tabWidget {
                                id = android.R.id.tabs
                                backgroundColor = Color.WHITE
                            }.lparams {
                                width = matchParent
                                height = dip(48)
                            }
                        }
                    }
                }

                frameLayout {
                    id = ID_SPLASH_LAYOUT
                    addView(mImageView)

                    button("跳过") {
                        backgroundColor = Color.RED
                        onClick { goneSplashView() }
                    }.lparams() {
                        width = wrapContent
                        height = wrapContent
                        gravity = Gravity.END
                        margin = dip(12)
                    }
                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("onViewCreated")

        mSplashView = view.findViewById(ID_SPLASH_LAYOUT) as _FrameLayout

        val homeStr = "首页"
        val applyStr = "申请"
        val myStr = "我的"
        val moreStr = "更多"

        val homeView = IndicatorUI(homeStr, R.drawable.selector_drawable_tab_home).createView(AnkoContext.Companion.create(context, this, false))
        val applyView = IndicatorUI(applyStr, R.drawable.selector_drawable_tab_apply).createView(AnkoContext.Companion.create(context, this, false))
        val myView = IndicatorUI(myStr, R.drawable.selector_drawable_tab_my).createView(AnkoContext.Companion.create(context, this, false))
        val moreView = IndicatorUI(moreStr, R.drawable.selector_drawable_tab_more).createView(AnkoContext.Companion.create(context, this, false))

        val tabHost: FragmentTabHost = view.findViewById(android.R.id.tabhost) as FragmentTabHost
        tabHost.setup(activity, activity.supportFragmentManager, android.R.id.tabcontent)
        tabHost.tabWidget.dividerDrawable = null
        tabHost.addTab(tabHost.newTabSpec(homeStr).setIndicator(homeView), HomeTabFragment::class.java, null)
        tabHost.addTab(tabHost.newTabSpec(applyStr).setIndicator(applyView), ApplyTabFragment::class.java, null)
        tabHost.addTab(tabHost.newTabSpec(myStr).setIndicator(myView), MyTabFragment::class.java, null)
        tabHost.addTab(tabHost.newTabSpec(moreStr).setIndicator(moreView), MoreTabFragment::class.java, null)
    }

    override fun onResume() {
        super.onResume()

        mPresenter.start()
    }

    override fun setSplashImage(uriStr: String) {
        try {
            mImageView.setImageURI(uriStr, context)
        } catch(e: Exception) {
            Timber.e("启动图片异常:%s", e.message)
        }
    }

    override fun goneSplashView() {
        Observable.timer(5, TimeUnit.SECONDS).bindToLifecycle(this).observeOn(AndroidSchedulers.mainThread())
                .subscribe { next ->
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

                    val scaleXAnim = ObjectAnimator.ofFloat(mSplashView, "scaleX", 1f, 2f)
                    scaleXAnim.duration = 400
                    val scaleYAnim = ObjectAnimator.ofFloat(mSplashView, "scaleY", 1f, 2f)
                    scaleYAnim.duration = 400
                    val alphaAnim = ObjectAnimator.ofFloat(mSplashView, "alpha", 1f, 0f)
                    alphaAnim.duration = 400
                    val animatorSet = AnimatorSet()
                    animatorSet.play(alphaAnim).with(scaleXAnim).with(scaleYAnim)
                    animatorSet.start()
                    animatorSet.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            mSplashView.visibility = View.GONE
                        }
                    })
                }
    }

    companion object {

        val ID_SPLASH_LAYOUT = 1001

        fun newInstance(): MainFragment {
            return MainFragment()
        }

        class IndicatorUI(val txt: String, val img: Int) : AnkoComponent<MainFragment> {
            override fun createView(ui: AnkoContext<MainFragment>) = with(ui) {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    backgroundColor = Color.parseColor("#F8F8F8")
                    gravity = Gravity.CENTER

                    imageView {
                        scaleType = ImageView.ScaleType.FIT_XY
                        imageResource = img
                    }.lparams {
                        width = dip(20)
                        height = dip(20)
                    }

                    textView {
                        setTextColor(AppCompatResources.getColorStateList(ctx, R.color.selector_color_tab_text))
                        text = txt
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                    }
                }
            }
        }
    }

}