package com.cardvlaue.sys.about

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
import android.widget.TextView
import com.cardvlaue.sys.R
import com.cardvlaue.sys.util.AnkoComponents
import com.cardvlaue.sys.util.DeviceUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class AboutFragment : Fragment(), AboutContract.View {

    private lateinit var mPresenter: AboutContract.Presenter

    private lateinit var mToolbarView: Toolbar

    private lateinit var mTextView: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mToolbarView = AnkoComponents.ToolbarBlueHasBackComponent("关于小企额", { activity.finish() })
                .createView(AnkoContext.Companion.create(context))

        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)

                /**
                 * Toolbar
                 */
                addView(mToolbarView)

                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    backgroundColor = Color.WHITE
                    gravity = Gravity.CENTER_HORIZONTAL
                    topPadding = dip(16)
                    bottomPadding = dip(16)

                    imageView {
                        imageResource = R.mipmap.icon_about_logo
                    }

                    textView("版本号:${ DeviceUtils.versionName(ctx) }") {
                        textColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                        textSize = 16f
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(8)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                view {
                    backgroundColor = Color.parseColor("#DBDBDB")
                }.lparams {
                    width = matchParent
                    height = 1
                    topMargin = dip(16)
                }

                linearLayout {
                    backgroundColor = Color.WHITE
                    gravity = Gravity.CENTER_VERTICAL
                    leftPadding = dip(16)
                    rightPadding = dip(16)

                    onClick { mPresenter.clickClearCache() }

                    textView("清除缓存") {  }

                    view {  }.lparams {
                        width = 0
                        height = wrapContent
                        weight = 1f
                    }

                    textView {
                        id = ID_CACHE_TEXT
                        textColor = R.color.app_main_color
                    }.lparams {
                        leftMargin = dip(16)
                        rightMargin = dip(16)
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

                view {
                    backgroundColor = Color.parseColor("#DBDBDB")
                }.lparams {
                    width = matchParent
                    height = 1
                }

            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(mToolbarView)
        mToolbarView.setContentInsetsRelative(0, 0)

        mTextView = view.findViewById(ID_CACHE_TEXT) as TextView
    }

    override fun onResume() {
        super.onResume()

        mPresenter.start()
    }

    override fun setCacheText(string: String) {
        mTextView.text = "共有缓存 $string"
    }

    override fun setPresenter(presenter: AboutContract.Presenter) {
        mPresenter = presenter
    }

    companion object {

        val ID_CACHE_TEXT = 1001

        fun newInstance() : AboutFragment {
            return AboutFragment()
        }
    }

}