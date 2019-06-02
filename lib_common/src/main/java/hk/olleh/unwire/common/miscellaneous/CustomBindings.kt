package hk.olleh.unwire.common.miscellaneous

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hk.olleh.unwire.common.R

object CustomBindings {

    @BindingAdapter("remote")
    @JvmStatic
    fun loadRemoteSource(view: ImageView, url: String?) {

        if (url != null) {

            Glide
                .with(view.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.img_splashing_logo)
                .into(view)

        } else {

            Glide
                .with(view.context)
                .load(R.drawable.img_splashing_logo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
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