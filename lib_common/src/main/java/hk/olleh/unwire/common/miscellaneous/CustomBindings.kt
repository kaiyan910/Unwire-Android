package hk.olleh.unwire.common.miscellaneous

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout

object CustomBindings {

    @BindingAdapter("remote")
    @JvmStatic
    fun loadRemoteSource(view: ImageView, src: String?) {

        src?.let {

            Glide
                .with(view.context)
                .load(it)
                .into(view)
        }
    }

    @BindingAdapter("onRefreshListener")
    @JvmStatic
    fun onRefreshListener(view: SwipeRefreshLayout, listener: () -> Unit) {

        view.setOnRefreshListener { listener.invoke() }
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

        html?.let {

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

            view.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
        }
    }
}