package com.cardvlaue.sys.util

import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.webkit.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.cardvlaue.sys.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

/**
 * 浏览器
 */
class WebLoadFragment : DialogFragment() {

    private lateinit var mToolbarView: Toolbar

    private lateinit var mProgressBarView: ProgressBar

    private lateinit var mWebView: _WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar)
        else
            setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mToolbarView = AnkoComponents.ToolbarBlueHasBackComponent(arguments.getString(ARGUMENT_WEB_LOAD_TITLE), { dismiss() })
                .createView(AnkoContext.create(context))
        mProgressBarView = inflater.inflate(R.layout.progress_bar_web_load, null) as ProgressBar

        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)

                addView(mToolbarView)

                addView(mProgressBarView)

                frameLayout {

                    imageView {
                        backgroundResource = R.mipmap.img_none
                    }.lparams {
                        gravity = Gravity.CENTER
                    }

                    webView {
                        id = ID_WEB_VIEW
                        backgroundColor = ContextCompat.getColor(ctx, R.color.app_background_color)
                    }.lparams {
                        width = matchParent
                        height = matchParent
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

        mWebView = view.findViewById(ID_WEB_VIEW) as _WebView
        mWebView.settings.javaScriptEnabled = true
        mWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                goneProgressBar()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                goneWebView()
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                @Suppress("DEPRECATION")
                super.onReceivedError(view, errorCode, description, failingUrl)
                goneWebView()
            }
        })
        mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                mProgressBarView.progress = newProgress
                if (newProgress == 100) mProgressBarView.visibility = View.GONE

                super.onProgressChanged(view, newProgress)
            }
        })
        /**
         * 返回上一页
         */
        mWebView.isFocusableInTouchMode = true
        mWebView.requestFocus()
        mWebView.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                mWebView.goBack()
                return@setOnKeyListener true
            }

            false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mWebView.loadUrl(arguments.getString(ARGUMENT_WEB_LOAD_URL))
    }

    private fun goneProgressBar() {
        if (mProgressBarView.isShown)
            mProgressBarView.visibility = View.GONE
    }

    private fun goneWebView() {
        goneProgressBar()
        if (mWebView.isShown)
            mWebView.visibility = View.GONE
    }

    companion object {
        val ID_WEB_VIEW = 1001

        val ARGUMENT_WEB_LOAD_TITLE = "title"
        val ARGUMENT_WEB_LOAD_URL = "url"

        fun newInstance(title: String, urlStr: String) : WebLoadFragment {
            val f = WebLoadFragment()
            val args = Bundle()
            args.putString(ARGUMENT_WEB_LOAD_TITLE, title)
            args.putString(ARGUMENT_WEB_LOAD_URL, urlStr)
            f.arguments = args
            return f
        }
    }

}