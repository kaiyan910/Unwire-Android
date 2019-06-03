package hk.olleh.unwire.common.miscellaneous

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import timber.log.Timber

object CustomBindings {

    @BindingAdapter("remote")
    @JvmStatic
    fun loadRemoteSource(view: ImageView, url: String?) {

        Timber.d("[DEBUG] load remote image=[$url]")

        if (url != null) {

            Glide
                .with(view.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(hk.olleh.unwire.common.R.drawable.img_splashing_logo)
                .into(view)

        } else {

            Glide
                .with(view.context)
                .load(hk.olleh.unwire.common.R.drawable.img_splashing_logo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }

    @BindingAdapter("onActionListener")
    @JvmStatic
    fun onActionListener(view: EditText, listener: (() -> Unit)?) {

        view.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Timber.d("[DEBUG] =123 called onActionListener")
                listener?.invoke()
                val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            false
        }
    }

    @BindingAdapter("onRefreshListener")
    @JvmStatic
    fun onRefreshListener(view: SwipeRefreshLayout, listener: () -> Unit) {

        view.setOnRefreshListener { listener.invoke() }
    }

    @BindingAdapter("html")
    @JvmStatic
    fun loadHTML(view: TextView, html: String?) {

        html?.let {

            view.setLinkTextColor(Color.BLACK)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            } else {
                view.text = Html.fromHtml(it)
            }
        }
    }

    @BindingAdapter(value = ["html", "dark"], requireAll = true)
    @JvmStatic
    fun loadHtml(view: WebView, html: String?, dark: Boolean) {

        val background = if (dark) {
            "#000000"
        } else {
            "#FFFFFF"
        }

        val color = if (dark) {
            "#C4C4C4"
        } else {
            "#000000"
        }

        html
            ?.let {

                val data = """
                    <html>
                        <head>
                            <meta name="viewport" content="width=device-width, initial-scale=1">
                            <style>
                                img{max-width: 100%; width:auto; height: auto;}
                                body {
                                    background-color: $background;
                                    color: $color;
                                    padding: 16px;
                                }
                                a {
                                    color: #00f481;
                                }
                            </style>
                        </head>
                        <body>$html</body>
                    </html>
                """.trimIndent()

                view
                    .apply {
                        settings.javaScriptEnabled = true
                        loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
                    }
            }
    }
}