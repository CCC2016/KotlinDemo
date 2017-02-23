package com.cardvlaue.sys.tabmy

import android.content.Context
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
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.cardvlaue.sys.MyApplication
import com.cardvlaue.sys.R
import com.cardvlaue.sys.util.AnkoComponents
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import javax.inject.Inject

class MyTabFragment : Fragment(), MyTabContract.View {

    @Inject
    lateinit var mPresenter: MyTabPresenter

    private lateinit var mToolbarView: Toolbar

    private lateinit var mShopNumberView: TextView

    private lateinit var mGiftNumberView: TextView

    private lateinit var mInviteNumberView: TextView

    private lateinit var mGiftSurplusView: TextView

    private lateinit var mFriendSurplusView: TextView

    override fun setPresenter(presenter: MyTabContract.Presenter) {}

    override fun setShopNumber(string: String) {
        mShopNumberView.text = string
    }

    override fun setGiftNumber(string: String) {
        mGiftNumberView.text = string
    }

    override fun setInviteNumber(string: String) {
        mInviteNumberView.text = string
    }

    override fun setGiftSurplus(string: String) {
        mGiftSurplusView.text = string
    }

    override fun setFriendSurplus(string: String) {
        mFriendSurplusView.text = string
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMyTabComponent.builder().myTabPresenterModule(MyTabPresenterModule(this))
                .tasksRepositoryComponent((activity.application as MyApplication).getTasksRepositoryComponent()).build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mToolbarView = AnkoComponents.ToolbarBlueNoBackComponent("我的").createView(AnkoContext.Companion.create(context))

        val containerView = inflater.inflate(R.layout.rebound_scroll_view_tab_my, container, false)
        val contentView = containerView.findViewById(R.id.fl_my_content) as FrameLayout
        val childView = MyContentUI().createView(AnkoContext.Companion.create(context))
        contentView.addView(childView)

        return UI {
            frameLayout {

                linearLayout {
                    orientation = LinearLayout.VERTICAL

                    view {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        weight = 1f
                    }

                    view {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        weight = 1f
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }

                linearLayout {
                    orientation = LinearLayout.VERTICAL

                    addView(mToolbarView)

                    /**
                     * 可滑动主体的布局
                     */
                    addView(containerView)
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

        mShopNumberView = view.findViewById(ID_SHOP_NUMBER) as TextView
        mGiftNumberView = view.findViewById(ID_GIFT_NUMBER) as TextView
        mInviteNumberView = view.findViewById(ID_INVITE_NUMBER) as TextView
        mGiftSurplusView = view.findViewById(ID_GIFT_SURPLUS) as TextView
        mFriendSurplusView = view.findViewById(ID_FRIEND_SURPLUS) as TextView
    }

    override fun onResume() {
        super.onResume()

        mPresenter.start()
    }

    /**
     * 可滑动视图
     */
    class MyContentUI() : AnkoComponent<Context> {
        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            val shopView = MyNumberUI(ID_SHOP_NUMBER, "我的店铺").createView(AnkoContext.Companion.create(ui.ctx))
            val giftView = MyNumberUI(ID_GIFT_NUMBER, "我的红包").createView(AnkoContext.Companion.create(ui.ctx))
            val inviteView = MyNumberUI(ID_INVITE_NUMBER, "我的邀请").createView(AnkoContext.Companion.create(ui.ctx))

            val smView = AnkoComponents.MoreItemUI(R.mipmap.icon_my_shop_manage, "店铺管理").createView(AnkoContext.Companion.create(ui.ctx))
            val qrCodeView = AnkoComponents.MoreItemUI(R.mipmap.icon_my_qrcode, "我的二维码").createView(AnkoContext.Companion.create(ui.ctx))
            val giftTooView = SurplusGiftUI(ID_GIFT_SURPLUS, R.mipmap.icon_my_gift, "我的红包").createView(AnkoContext.Companion.create(ui.ctx, this))
            val inviteAreaView = InviteAreaUI(R.mipmap.icon_my_shop_manage, "邀请专区").createView(AnkoContext.Companion.create(ui.ctx, this))
            val inviteFriendsView = InviteFriendsItemUI(ID_FRIEND_SURPLUS, "邀请好友赢红包").createView(AnkoContext.Companion.create(ui.ctx, this))

            linearLayout {
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                    gravity = Gravity.CENTER_VERTICAL

                    imageView {
                        imageResource = R.mipmap.icon_avatar
                    }.lparams {
                        width = dip(56)
                        height = dip(56)
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        textView("您好，游客！") {
                            textColor = Color.WHITE
                            textSize = 20f
                        }

                        textView("--") {
                            textColor = Color.WHITE
                            setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_my_phone, 0, 0, 0)
                            compoundDrawablePadding = dip(4)
                        }.lparams {
                            topMargin = dip(8)
                        }
                    }.lparams {
                        width = 0
                        height = wrapContent
                        weight = 1f
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                    }

                    imageView {
                        imageResource = R.mipmap.icon_more_white
                    }.lparams {
                        width = dip(32)
                        height = dip(32)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(92)
                }

                linearLayout {
                    weightSum = 3f
                    backgroundColor = Color.WHITE

                    /**
                     * 我的店铺
                     */
                    addView(shopView)
                    /**
                     * 我的红包
                     */
                    addView(giftView)
                    /**
                     * 我的邀请
                     */
                    addView(inviteView)
                }.lparams {
                    width = matchParent
                    height = dip(70)
                }

                view {
                    backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)
                }.lparams {
                    width = matchParent
                    height = dip(8)
                }

                /**
                 * 店铺管理
                 */
                addView(smView)

                view {
                    backgroundColor = Color.parseColor("#E8E8E8")
                }.lparams {
                    width = matchParent
                    height = 1
                }

                /**
                 * 我的二维码
                 */
                addView(qrCodeView)

                view {
                    backgroundColor = Color.parseColor("#E8E8E8")
                }.lparams {
                    width = matchParent
                    height = 1
                }

                /**
                 * 我的红包
                 */
                addView(giftTooView)

                view {
                    backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)
                }.lparams {
                    width = matchParent
                    height = dip(8)
                }

                /**
                 * 邀请专区
                 */
                addView(inviteAreaView)

                view {
                    backgroundColor = Color.parseColor("#E8E8E8")
                }.lparams {
                    width = matchParent
                    height = 1
                }

                /**
                 * 邀请好友
                 */
                addView(inviteFriendsView)
            }
        }
    }

    /**
     * 我的店铺、我的红包、我的邀请 共用布局组件
     */
    class MyNumberUI(val wId: Int, val title: String) : AnkoComponent<Context> {
        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                lparams {
                    weight = 1f
                    width = 0
                    height = matchParent
                }

                textView(title) {
                    textColor = Color.parseColor("#6E6E6E")
                    textSize = 12f
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }

                linearLayout {

                    textView {
                        id = wId
                        textColor = Color.parseColor("#FF9000")
                        textSize = 20f
                    }

                    textView {
                        textColor = Color.parseColor("#FF9000")
                        textSize = 12f
                        text = " 个"
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }
            }
        }
    }

    /**
     * 我的剩余红包
     */
    class SurplusGiftUI(val wId: Int, val img: Int, val txt: String) : AnkoComponent<Any> {
        override fun createView(ui: AnkoContext<Any>) = with(ui) {
            frameLayout {
                linearLayout {
                    backgroundColor = Color.WHITE
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                    gravity = Gravity.CENTER_VERTICAL

                    imageView {
                        imageResource = img
                    }.lparams {
                        width = dip(24)
                        height = dip(24)
                    }

                    textView(txt) {
                        textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                        textSize = 14f
                    }.lparams {
                        width = 0
                        weight = 1f
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                    }

                    textView {
                        id = wId
                        textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                    }

                    imageView {
                        imageResource = R.mipmap.icon_more_gray
                    }.lparams {
                        width = dip(12)
                        height = dip(12)
                    }

                }.lparams {
                    width = matchParent
                    height = dip(48)
                }
            }
        }
    }

    /**
     *邀请专区
     */
    class InviteAreaUI(val img: Int, val txt: String) : AnkoComponent<Any> {
        override fun createView(ui: AnkoContext<Any>) = with(ui) {
            frameLayout {
                linearLayout {
                    backgroundColor = Color.WHITE
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                    gravity = Gravity.CENTER_VERTICAL

                    imageView {
                        imageResource = img
                    }.lparams {
                        width = dip(24)
                        height = dip(24)
                    }

                    textView(txt) {
                        textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                        textSize = 14f
                    }.lparams {
                        width = 0
                        weight = 1f
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(48)
                }
            }
        }
    }

    /**
     * 邀请好友
     */
    class InviteFriendsItemUI(val wId: Int, val txt: String) : AnkoComponent<Any> {
        override fun createView(ui: AnkoContext<Any>) = with(ui) {
            frameLayout {
                linearLayout {
                    backgroundColor = Color.WHITE
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                    gravity = Gravity.CENTER_VERTICAL

                    textView(txt) {
                        textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                        textSize = 13f
                    }.lparams {
                        width = 0
                        weight = 1f
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                    }

                    textView {
                        id = wId
                        textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                        textSize = 13f
                    }

                    imageView {
                        imageResource = R.mipmap.icon_more_gray
                    }.lparams {
                        width = dip(12)
                        height = dip(12)
                    }

                }.lparams {
                    width = matchParent
                    height = dip(48)
                }
            }
        }
    }

    companion object {
        val ID_SHOP_NUMBER = 1001
        val ID_GIFT_NUMBER = 1002
        val ID_INVITE_NUMBER = 1003
        val ID_GIFT_SURPLUS = 1004
        val ID_FRIEND_SURPLUS = 1005
    }

}