package com.cardvlaue.sys.tabapply

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.cardvlaue.sys.MyApplication
import com.cardvlaue.sys.R
import com.cardvlaue.sys.util.AnkoComponents
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import javax.inject.Inject

/**
 * 申请
 */
class ApplyTabFragment : Fragment(), ApplyTabContract.View {

    @Inject
    lateinit var mPresenter: ApplyTabPresenter

    private lateinit var mToolbarView: Toolbar

    private lateinit var mAdapter: ApplyListsAdapter

    override fun setPresenter(presenter: ApplyTabContract.Presenter) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerApplyTabComponent.builder().applyTabPresenterModule(ApplyTabPresenterModule(this))
                .tasksRepositoryComponent((activity.application as MyApplication).getTasksRepositoryComponent())
                .build().inject(this)

        mAdapter = ApplyListsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mToolbarView = AnkoComponents.ToolbarWhiteNoBackComponent("申请").createView(AnkoContext.Companion.create(context))

        val containerView = inflater.inflate(R.layout.rebound_scroll_view_tab_my, container, false)
        val contentView = containerView.findViewById(R.id.fl_my_content) as FrameLayout
        val childView = ApplyListsUI().createView(AnkoContext.Companion.create(context))
        contentView.addView(childView)

        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)

                addView(mToolbarView)

                frameLayout {

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER

                        imageView {
                            imageResource = R.mipmap.img_apply_none
                        }.lparams {
                            topMargin = dip(100)
                        }

                        textView("暂无内容") {
                            textSize = 20f
                            textColor = ContextCompat.getColor(ctx, R.color.app_main_color)
                        }.lparams {
                            topMargin = dip(20)
                        }

                        textView("您当前还没有一笔融资记录，赶快去申请吧！") {
                            textSize = 12f
                            textColor = ContextCompat.getColor(ctx, R.color.text_small_color)
                        }.lparams {
                            topMargin = dip(10)
                        }

                        button("立即申请") {
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.selector_drawable_home_button
                        }.lparams {
                            width = dip(140)
                            height = dip(40)
                            topMargin = dip(20)
                        }
                    }

                    frameLayout {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)

                        addView(containerView)
                    }
                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(mToolbarView)
        mToolbarView.setContentInsetsRelative(0, 0)

        val listsView = view.findViewById(ID_RECYCLER_VIEW) as RecyclerView
        listsView.adapter = mAdapter
        listsView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
        super.onResume()

        mPresenter.start()
    }

    override fun updateAdapter(list: List<ApplyListsItemBean>) {
        mAdapter.updateData(list)
    }

    companion object {
        val ID_RECYCLER_VIEW = 1001
    }

    class ApplyListsUI : AnkoComponent<Context> {
        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            recyclerView {
                id = ID_RECYCLER_VIEW
                backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)
                lparams {
                    width = matchParent
                    height = wrapContent
                }
            }
        }
    }

}