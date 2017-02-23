package com.cardvlaue.sys.tabmore

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.cardvlaue.sys.R
import com.cardvlaue.sys.util.AnkoComponents
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import javax.inject.Inject

class MoreTabFragment : Fragment(), MoreTabContract.View {

    @Inject
    lateinit var mPresenter: MoreTabPresenter

    private lateinit var mToolbarView: Toolbar

    override fun setPresenter(presenter: MoreTabContract.Presenter) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMoreTabComponent.builder().moreTabPresenterModule(MoreTabPresenterModule(this, fragmentManager, context)).build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mToolbarView = AnkoComponents.ToolbarBlueNoBackComponent("更多").createView(AnkoContext.Companion.create(context))

        val strategyView = AnkoComponents.MoreItemUI(R.mipmap.icon_more_strategy, STR_STRATEGY).createView(AnkoContext.Companion.create(context))
        val introductionView = AnkoComponents.MoreItemUI(R.mipmap.icon_more_introduction, STR_INTRODUCTION).createView(AnkoContext.Companion.create(context))
        val honourView = AnkoComponents.MoreItemUI(R.mipmap.icon_more_honour, STR_HONOUR).createView(AnkoContext.Companion.create(context))
        val contactView = AnkoComponents.MoreItemUI(R.mipmap.icon_more_contact, STR_CONTRACT).createView(AnkoContext.Companion.create(context))
        val aboutView = AnkoComponents.MoreItemUI(R.mipmap.icon_more_about, "关于我们").createView(AnkoContext.Companion.create(context))
        val feedbackView = AnkoComponents.MoreItemUI(R.mipmap.icon_more_feedback, "意见反馈").createView(AnkoContext.Companion.create(context))
        val serviceView = AnkoComponents.MoreItemUI(R.mipmap.icon_more_service, "在线客服").createView(AnkoContext.Companion.create(context))

        strategyView.onClick { mPresenter.clickStrategy() }
        introductionView.onClick { mPresenter.clickIntroduction() }
        honourView.onClick { mPresenter.clickHonour() }
        contactView.onClick { mPresenter.clickContact() }
        aboutView.onClick { mPresenter.clickAbout() }

        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)

                addView(mToolbarView)

                scrollView {

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        linearLayout {
                            backgroundColor = Color.WHITE

                            imageView {
                                imageResource = R.mipmap.icon_more_phone
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                leftMargin = dip(40)
                                rightMargin = dip(16)
                            }

                            linearLayout {
                                orientation = LinearLayout.VERTICAL

                                textView("4008-803-803") {
                                    textSize = 17f
                                    textColor = Color.BLACK
                                }

                                textView("服务时间周一至周五：9:00-18:00") {
                                    textSize = 12f
                                    textColor = Color.parseColor("#606060")
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                            }

                            onClick { mPresenter.clickPhone() }
                        }.lparams {
                            width = matchParent
                            height = dip(88)
                        }

                        view {  }.lparams {
                            height = dip(8)
                        }

                        /**
                         * 融资攻略
                         */
                        addView(strategyView)

                        view {  }.lparams {
                            height = dip(8)
                        }

                        /**
                         * 公司简介
                         */
                        addView(introductionView)

                        view {
                            backgroundColor = Color.parseColor("#E8E8E8")
                        }.lparams {
                            height = 1
                        }

                        /**
                         * 资质荣誉
                         */
                        addView(honourView)

                        view {
                            backgroundColor = Color.parseColor("#E8E8E8")
                        }.lparams {
                            height = 1
                        }

                        /**
                         * 联系方式
                         */
                        addView(contactView)

                        view {
                            backgroundColor = Color.parseColor("#E8E8E8")
                        }.lparams {
                            height = 1
                        }

                        /**
                         * 关于我们
                         */
                        addView(aboutView)

                        view {  }.lparams {
                            height = dip(8)
                        }

                        /**
                         * 意见反馈
                         */
                        addView(feedbackView)

                        view {
                            backgroundColor = Color.parseColor("#E8E8E8")
                        }.lparams {
                            height = 1
                        }

                        /**
                         * 在线客服
                         */
                        addView(serviceView)
                    }

                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(mToolbarView)
        mToolbarView.setContentInsetsRelative(0, 0)
    }

    companion object {
        val STR_STRATEGY = "融资攻略"
        val STR_INTRODUCTION = "公司简介"
        val STR_HONOUR = "资质荣誉"
        val STR_CONTRACT = "联系方式"
    }

}