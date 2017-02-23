package com.cardvlaue.sys.tabhome

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.cardvlaue.sys.MyApplication
import com.cardvlaue.sys.R
import com.cardvlaue.sys.financeintent.FinanceIntentActivity
import com.cardvlaue.sys.tabmore.MoreTabContract
import com.cardvlaue.sys.view.VpSwipeRefreshLayout
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.viewPager
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeTabFragment : RxFragment(), HomeTabContract.View {

    @Inject
    lateinit var mPresenter: HomeTabPresenter

    private lateinit var mAdView: SimpleDraweeView

    private lateinit var mAdapter: HomeLoopPagerAdapter

    private lateinit var mViewPager: ViewPager

    private lateinit var mRefreshView: VpSwipeRefreshLayout

    override fun setPresenter(presenter: MoreTabContract.Presenter) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerHomeTabComponent.builder()
                .tasksRepositoryComponent((activity.application as MyApplication).getTasksRepositoryComponent())
                .homeTabPresenterModule(HomeTabPresenterModule(this, context)).build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mAdView = SimpleDraweeView(context)
        mAdView.hierarchy = GenericDraweeHierarchyBuilder(resources)
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImage(R.mipmap.img_home_ad)
                .build()
        mAdView.layoutParams = ViewGroup.LayoutParams(matchParent, dip(180))

        mRefreshView = VpSwipeRefreshLayout(context)

        val view = UI {
            scrollView {
                isVerticalScrollBarEnabled = false
                backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)

                linearLayout {
                    orientation = LinearLayout.VERTICAL

                    lparams {
                        width = matchParent
                        height = matchParent
                    }

                    addView(mAdView)

                    view {
                        backgroundColor = Color.parseColor("#E8E8E8")
                    }.lparams {
                        width = matchParent
                        height = 1
                    }

                    linearLayout {
                        leftPadding = dip(16)
                        rightPadding = dip(16)
                        gravity = Gravity.CENTER_VERTICAL
                        backgroundColor = Color.WHITE

                        imageView {
                            imageResource = R.mipmap.icon_home_notice
                        }.lparams {
                            width = dip(16)
                            height = dip(16)
                            rightMargin = dip(8)
                        }

                        /**
                         * 公告
                         */
                        viewFlipper {
                            isAutoStart = true
                            setFlipInterval(5000)
                            setInAnimation(ctx, R.anim.anim_home_notice_text_in)
                            setOutAnimation(ctx, R.anim.anim_home_notice_text_out)

                            textView("暂无公告 一") {
                                id = ID_NOTICE_ONE
                                textSize = 14f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                            }

                            textView("暂无公告 二") {
                                id = ID_NOTICE_TWO
                                textSize = 14f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                            }

                            textView("暂无公告 三") {
                                id = ID_NOTICE_THREE
                                textSize = 14f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                            }
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(41)
                    }

                    /**
                     * 轮播图
                     */
                    viewPager {
                        id = ID_VIEW_PAGER
                        backgroundColor = Color.WHITE
                    }.lparams {
                        width = matchParent
                        height = dip(130)
                        topMargin = dip(8)
                        bottomMargin = dip(8)
                    }

                    linearLayout {
                        leftPadding = dip(16)
                        rightPadding = dip(16)
                        gravity = Gravity.CENTER_VERTICAL
                        backgroundColor = Color.WHITE

                        imageView {
                            imageResource = R.mipmap.icon_home_money
                        }.lparams {
                            width = dip(24)
                            height = dip(24)
                        }

                        textView("明日营收 今日变现") {
                            textSize = 15f
                            textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                        }.lparams {
                            width = 0
                            weight = 1f
                            leftMargin = dip(8)
                            rightMargin = dip(8)
                        }

                        imageView {
                            imageResource = R.mipmap.img_home_apply
                        }.lparams {
                            width = dip(114)
                            height = dip(26)
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(50)
                    }

                    view {
                        backgroundColor = Color.parseColor("#E5E5E5")
                    }.lparams {
                        width = matchParent
                        height = 1
                    }

                    linearLayout {
                        backgroundColor = Color.WHITE
                        gravity = Gravity.CENTER_VERTICAL
                        leftPadding = dip(25)
                        rightPadding = dip(19)

                        relativeLayout {
                            textView("最高可贷(万)") {
                                textSize = 10f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                            }

                            textView("300") {
                                textSize = 25f
                                textColor = Color.parseColor("#FF9000")
                            }.lparams {
                                alignParentBottom()
                            }
                        }.lparams {
                            width = wrapContent
                            height = dip(52)
                        }

                        relativeLayout {
                            textView("月费率低至(%)") {
                                textSize = 10f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                            }

                            textView("1.5") {
                                textSize = 17f
                                textColor = Color.parseColor("#FF9000")
                            }.lparams {
                                alignParentBottom()
                                bottomMargin = dip(4)
                            }
                        }.lparams {
                            width = wrapContent
                            height = dip(52)
                            leftMargin = dip(25)
                        }

                        /**
                         * 申请按钮
                         */
                        frameLayout {
                            view {
                                backgroundColor = Color.parseColor("#E1E1E1")
                            }.lparams {
                                width = 1
                                height = matchParent
                                topMargin = dip(22)
                                bottomMargin = dip(22)
                                gravity = Gravity.CENTER
                            }
                        }.lparams {
                            width = 0
                            height = matchParent
                            weight = 1f
                        }

                        button("立即申请") {
                            textSize = 15f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.selector_drawable_home_button
                            onClick { startActivity(Intent(ctx, FinanceIntentActivity::class.java)) }
                        }.lparams {
                            width = dip(109)
                            height = dip(46)
                        }

                    }.lparams {
                        width = matchParent
                        height = dip(112)
                    }

                    /**
                     * 四个产品介绍
                     */
                    tableLayout {
                        backgroundColor = Color.WHITE

                        tableRow {
                            linearLayout {
                                leftPadding = dip(25)
                                rightPadding = dip(25)
                                gravity = Gravity.CENTER_VERTICAL

                                linearLayout {
                                    orientation = LinearLayout.VERTICAL

                                    textView("首批国家级") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                                        textSize = 13f
                                    }

                                    textView("商业保理试点企业") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                                        textSize = 10f
                                    }.lparams { topMargin = dip(5) }
                                }.lparams {
                                    width = 0
                                    height = wrapContent
                                    weight = 1f
                                }

                                imageView {
                                    imageResource = R.mipmap.icon_home_one
                                }.lparams {
                                    width = dip(30)
                                    height = dip(30)
                                }
                            }.lparams {
                                height = matchParent
                                weight = 1f
                            }

                            view { backgroundColor = Color.parseColor("#E7E7E7") }.lparams {
                                width = 1
                                height = matchParent
                            }

                            linearLayout {
                                leftPadding = dip(25)
                                rightPadding = dip(25)
                                gravity = Gravity.CENTER_VERTICAL

                                linearLayout {
                                    orientation = LinearLayout.VERTICAL

                                    textView("上海市政府认定") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                                        textSize = 13f
                                    }

                                    textView("中小企业服务机构") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                                        textSize = 10f
                                    }.lparams { topMargin = dip(5) }
                                }.lparams {
                                    width = 0
                                    height = wrapContent
                                    weight = 1f
                                }

                                imageView {
                                    imageResource = R.mipmap.icon_home_two
                                }.lparams {
                                    width = dip(30)
                                    height = dip(30)
                                }
                            }.lparams {
                                height = matchParent
                                weight = 1f
                            }
                        }.lparams { weight = 1f }

                        view { backgroundColor = Color.parseColor("#E7E7E7") }.lparams {
                            width = matchParent
                            height = 1
                        }

                        tableRow {
                            linearLayout {
                                leftPadding = dip(25)
                                rightPadding = dip(25)
                                gravity = Gravity.CENTER_VERTICAL

                                linearLayout {
                                    orientation = LinearLayout.VERTICAL

                                    textView("应收账款") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                                        textSize = 13f
                                    }

                                    textView("登记居全国首席") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                                        textSize = 10f
                                    }.lparams { topMargin = dip(5) }
                                }.lparams {
                                    width = 0
                                    height = wrapContent
                                    weight = 1f
                                }

                                imageView {
                                    imageResource = R.mipmap.icon_home_three
                                }.lparams {
                                    width = dip(30)
                                    height = dip(30)
                                }
                            }.lparams {
                                height = matchParent
                                weight = 1f
                            }

                            view { backgroundColor = Color.parseColor("#E7E7E7") }.lparams {
                                width = 1
                                height = matchParent
                            }

                            linearLayout {
                                leftPadding = dip(25)
                                rightPadding = dip(25)
                                gravity = Gravity.CENTER_VERTICAL

                                linearLayout {
                                    orientation = LinearLayout.VERTICAL

                                    textView("业务覆盖面积") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                                        textSize = 13f
                                    }

                                    textView("全国32省份和直辖市") {
                                        textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                                        textSize = 10f
                                    }.lparams { topMargin = dip(5) }
                                }.lparams {
                                    width = 0
                                    height = wrapContent
                                    weight = 1f
                                }

                                imageView {
                                    imageResource = R.mipmap.icon_home_four
                                }.lparams {
                                    width = dip(30)
                                    height = dip(30)
                                }
                            }.lparams {
                                height = matchParent
                                weight = 1f
                            }
                        }.lparams { weight = 1f }
                    }.lparams {
                        width = matchParent
                        height = dip(143)
                        topMargin = dip(11)
                        bottomMargin = dip(11)
                    }

                    /**
                     * 底部权威认证
                     */
                    frameLayout {
                        backgroundColor = Color.WHITE

                        imageView {
                            imageResource = R.mipmap.icon_home_info
                        }.lparams {
                            width = dip(108)
                            height = dip(21)
                            gravity = Gravity.CENTER
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(56)
                    }
                }
            }
        }.view

        mRefreshView.addView(view)

        return mRefreshView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = HomeLoopPagerAdapter()

        mViewPager = view.findViewById(ID_VIEW_PAGER) as ViewPager
        mViewPager.adapter = mAdapter
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0 && positionOffsetPixels == 0)
                    mViewPager.setCurrentItem(SIZE_VIEW_PAGER - 2, false)
                else if (position == SIZE_VIEW_PAGER - 1 && positionOffsetPixels == 0)
                    mViewPager.setCurrentItem(1, false)
            }

            override fun onPageSelected(position: Int) {
            }
        })

        mRefreshView.setColorSchemeResources(R.color.app_main_color)
    }

    override fun onResume() {
        super.onResume()

        mPresenter.start()
    }

    override fun onPause() {
        super.onPause()

        mRefreshView.isRefreshing = false
        mRefreshView.destroyDrawingCache()
        mRefreshView.clearAnimation()

        mViewPager.clearOnPageChangeListeners()
    }

    override fun startLoopPager() {
        Observable.interval(3, 3, TimeUnit.SECONDS).bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { next ->
                    var currentIndex = mViewPager.currentItem
                    if (++currentIndex == mAdapter.count) {
                        mViewPager.currentItem = 1
                    } else {
                        mViewPager.setCurrentItem(currentIndex, true)
                    }
                }
    }

    override fun setAdImage(urlStr: String) {
        mAdView.setImageURI(Uri.parse(urlStr), context)
    }

    override fun setPagerData(list: List<HomeLooperAdapterBean>) {
        mAdapter.updateData(list)
    }

    companion object {
        val ID_VIEW_PAGER = 1001
        val ID_NOTICE_ONE = 1002
        val ID_NOTICE_TWO = 1003
        val ID_NOTICE_THREE = 1004

        /**
         * 约定大小
         */
        val SIZE_VIEW_PAGER = 5
    }

}