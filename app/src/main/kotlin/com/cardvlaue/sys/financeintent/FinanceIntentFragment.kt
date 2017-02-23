package com.cardvlaue.sys.financeintent

import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.cardvlaue.sys.R
import com.cardvlaue.sys.util.AnkoComponents
import com.cardvlaue.sys.view.AddView
import com.cardvlaue.sys.view.DeleteView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.dip

class FinanceIntentFragment : Fragment(), FinanceIntentContract.View {

    private lateinit var mToolbarView: Toolbar

    override fun setPresenter(presenter: FinanceIntentContract.Presenter) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mToolbarView = AnkoComponents.ToolbarWhiteHasBackComponent("融资意向", { activity.finish() })
                .createView(AnkoContext.Companion.create(context))

        val financeShopView = AnkoComponents.FinanceIntentItemUI("融资店铺", "请选择", ID_FINANCE_SHOP_TEXT).createView(AnkoContext.Companion.create(context))
        val financeUseView = AnkoComponents.FinanceIntentItemUI("融资用途", "请选择", ID_FINANCE_USE_TEXT).createView(AnkoContext.Companion.create(context))
        val giftView = AnkoComponents.FinanceIntentItemUI("可用红包", "0元", ID_FINANCE_GIFT).createView(AnkoContext.Companion.create(context))

        val delView = DeleteView(context)
        delView.layoutParams = ViewGroup.LayoutParams(dip(20), dip(20))

        val addView = AddView(context)
        addView.layoutParams = ViewGroup.LayoutParams(dip(20), dip(20))

        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)
                isFocusable = true
                isFocusableInTouchMode = true

                addView(mToolbarView)

                scrollView {
                    isVerticalScrollBarEnabled = false

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        /**
                         * 店铺/用途
                         */
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            backgroundColor = Color.WHITE
                            leftPadding = dip(12)
                            rightPadding = dip(12)

                            frameLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                addView(financeShopView)
                            }.lparams {
                                width = matchParent
                                height = dip(62)
                            }

                            view {
                                backgroundColor = Color.parseColor("#E5E5E5")
                            }.lparams {
                                width = matchParent
                                height = 1
                            }

                            frameLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                addView(financeUseView)
                            }.lparams {
                                width = matchParent
                                height = dip(62)
                            }
                        }.lparams { topMargin = dip(10) }

                        /**
                         * 金额
                         */
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            backgroundColor = Color.WHITE
                            padding = dip(12)

                            textView("意向融资金额") {
                                textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                                textSize = 14f
                            }

                            textView("5,000 起，1,000 的倍数递增，最多可以 10,000,000 元") {
                                textSize = 13f
                                textColor = Color.parseColor("#909090")
                            }.lparams { topMargin = dip(6) }

                            /**
                             * 输入金额
                             */
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                addView(delView)

                                editText("5000") {
                                    textColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                                    textSize = 25f
                                    backgroundColor = Color.TRANSPARENT
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    imeOptions = EditorInfo.IME_ACTION_DONE
                                    inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                                    maxLines = 1
                                }.lparams {
                                    width = 0
                                    height = wrapContent
                                    weight = 1f
                                    leftMargin = dip(32)
                                    rightMargin = dip(32)
                                }

                                addView(addView)
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }

                            view {
                                backgroundColor = Color.parseColor("#E5E5E5")
                            }.lparams {
                                width = matchParent
                                height = 1
                                bottomMargin = dip(4)
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            topMargin = dip(10)
                        }

                        /**
                         * 期限
                         */
                        linearLayout {
                            backgroundColor = Color.WHITE
                            orientation = LinearLayout.VERTICAL
                            leftPadding = dip(12)
                            rightPadding = dip(12)

                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL

                                textView("意向融资期限") {
                                    textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                                    textSize = 14f
                                }

                                view { }.lparams { weight = 1f }

                                textView("三个月 月费率2.30%-2.50%") {
                                    textColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                                    textSize = 12f
                                    setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_finance_intent_tariff, 0, 0, 0)
                                    compoundDrawablePadding = dip(4)
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(45)
                            }

                            view { backgroundColor = Color.parseColor("#E5E5E5") }.lparams {
                                width = matchParent
                                height = 1
                            }

                            radioGroup {
                                orientation = RadioGroup.HORIZONTAL
                                gravity = Gravity.CENTER_VERTICAL

                                radioButton {
                                    text = "三个月"
                                    textSize = 14f
                                    setTextColor(AppCompatResources.getColorStateList(ctx, R.color.selector_color_intent_button))
                                    buttonDrawable = StateListDrawable()
                                    backgroundResource = R.drawable.selector_drawable_intent_button
                                    gravity = Gravity.CENTER
//                                    isChecked = true
                                }.lparams {
                                    width = 0
                                    height = dip(41)
                                    weight = 1f
                                }

                                radioButton {
                                    text = "六个月"
                                    textSize = 14f
                                    setTextColor(AppCompatResources.getColorStateList(ctx, R.color.selector_color_intent_button))
                                    buttonDrawable = StateListDrawable()
                                    backgroundResource = R.drawable.selector_drawable_intent_button
                                    gravity = Gravity.CENTER
                                }.lparams {
                                    width = 0
                                    height = dip(41)
                                    weight = 1f
                                    leftMargin = dip(12)
                                    rightMargin = dip(12)
                                }

                                radioButton {
                                    text = "九个月"
                                    textSize = 14f
                                    setTextColor(AppCompatResources.getColorStateList(ctx, R.color.selector_color_intent_button))
                                    buttonDrawable = StateListDrawable()
                                    backgroundResource = R.drawable.selector_drawable_intent_button
                                    gravity = Gravity.CENTER
                                }.lparams {
                                    width = 0
                                    height = dip(41)
                                    weight = 1f
                                }
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(125)
                            topMargin = dip(10)
                        }

                        /**
                         * 红包
                         */
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            backgroundColor = Color.WHITE
                            leftPadding = dip(12)
                            rightPadding = dip(12)
                            gravity = Gravity.CENTER_VERTICAL

                            frameLayout {
                                addView(giftView)
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }

                            textView("已按最优方案匹配红包") {
                                textSize = 13f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                            }.lparams { topMargin = dip(5) }
                        }.lparams {
                            width = matchParent
                            height = dip(77)
                            topMargin = dip(10)
                            bottomMargin = dip(12)
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = 0
                    weight = 1f
                }

                button("下一步") {
                    textColor = Color.WHITE
                    textSize = 16f
                    backgroundColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                }.lparams {
                    width = matchParent
                    height = dip(48)
                }
            }
        }.view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(mToolbarView)
        mToolbarView.setContentInsetsRelative(0, 0)
    }

    companion object {
        val ID_FINANCE_SHOP_TEXT = 1001
        val ID_FINANCE_USE_TEXT = 1002
        val ID_FINANCE_GIFT = 1003
    }

}