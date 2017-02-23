package com.cardvlaue.sys.tabapply

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.cardvlaue.sys.R
import org.jetbrains.anko.*

class ApplyListsAdapter : RecyclerView.Adapter<ApplyListsAdapter.ViewHolder>() {

    private var mData = arrayListOf<ApplyListsItemBean>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == mData.size - 1) (holder.contentView.layoutParams as LinearLayout.LayoutParams).bottomMargin = 32

        holder.timeView.text = mData[position].time
        holder.nameView.text = mData[position].name
        holder.limitView.text = mData[position].limit
        holder.termView.text = mData[position].term
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ApplyListItemUI().createView(AnkoContext.Companion.create(parent.context, this))

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun updateData(list: List<ApplyListsItemBean>) {
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * 最外布局
         */
        val contentView: _LinearLayout
        val timeView: TextView
        val nameView: TextView
        val limitView: TextView
        val termView: TextView

        init {
            contentView = itemView.findViewById(ID_APPLY_LISTS_ADAPTER_CONTENT) as _LinearLayout
            timeView = itemView.findViewById(ID_APPLY_TIME) as TextView
            nameView = itemView.findViewById(ID_APPLY_NAME) as TextView
            limitView = itemView.findViewById(ID_APPLY_LIMIT) as TextView
            termView = itemView.findViewById(ID_APPLY_TERM) as TextView
        }
    }

    class ApplyListItemUI : AnkoComponent<Any> {
        override fun createView(ui: AnkoContext<Any>): View = with(ui) {
            linearLayout {
                id = ID_APPLY_LISTS_ADAPTER_CONTENT
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.WHITE
                leftPadding = dip(14)
                rightPadding = dip(14)
                topPadding = dip(17)
                lparams {
                    width = matchParent
                    height = dip(230)
                    topMargin = dip(10)
                }

                textView {
                    id = ID_APPLY_TIME
                    textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                    textSize = 12f
                }.lparams {
                    bottomMargin = dip(17)
                }

                linearLayout {
                    imageView {
                        imageResource = R.mipmap.icon_apply_shop
                    }.lparams {
                        gravity = Gravity.CENTER_VERTICAL
                        rightMargin = dip(24)
                    }

                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id = ID_APPLY_NAME
                                textSize = 15f
                                textColor = ContextCompat.getColor(ctx, R.color.text_big_color)
                            }.lparams {
                                width = 0
                                height = wrapContent
                                weight = 1f
                            }

                            textView("申请中") {
                                backgroundResource = R.drawable.selector_drawable_home_button
                                textSize = 11f
                                textColor = Color.WHITE
                                leftPadding = dip(10)
                                rightPadding = dip(10)
                                topPadding = dip(5)
                                bottomPadding = dip(5)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(36)
                        }

                        view {
                            backgroundColor = Color.parseColor("#E5E5E5")
                        }.lparams {
                            width = matchParent
                            height = 1
                        }

                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            textView {
                                id = ID_APPLY_LIMIT
                                textSize = 12f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                                setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_apply_amount, 0, 0, 0)
                                compoundDrawablePadding = dip(4)
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                                height = 0
                                weight = 1f
                            }

                            textView {
                                id = ID_APPLY_TERM
                                textSize = 12f
                                textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                                setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_apply_time, 0, 0, 0)
                                compoundDrawablePadding = dip(4)
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                                height = 0
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = dip(100)
                }

                /**
                 * 按钮上方分割线
                 */
                view {
                    backgroundColor = Color.parseColor("#E5E5E5")
                }.lparams {
                    width = matchParent
                    height = 1
                    topMargin = dip(10)
                }

                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL

                    button("融资方案确认") {
                        textSize = 13f
                        textColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                        backgroundResource = R.drawable.shape_intent_button_checked
                    }.lparams {
                        width = 0
                        height = dip(34)
                        weight = 1f
                        margin = dip(4)
                    }

                    button("查看通知书") {
                        textSize = 13f
                        textColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                        backgroundResource = R.drawable.shape_intent_button_checked
                    }.lparams {
                        width = 0
                        height = dip(34)
                        weight = 1f
                        margin = dip(4)
                    }

                    button("继续申请") {
                        textSize = 13f
                        textColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                        backgroundResource = R.drawable.shape_intent_button_checked
                    }.lparams {
                        width = 0
                        height = dip(34)
                        weight = 1f
                        margin = dip(4)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }
    }

    companion object {
        val ID_APPLY_LISTS_ADAPTER_CONTENT = 1001
        val ID_APPLY_TIME = 1002
        val ID_APPLY_NAME = 1003
        val ID_APPLY_LIMIT = 1004
        val ID_APPLY_TERM = 1005
    }

}