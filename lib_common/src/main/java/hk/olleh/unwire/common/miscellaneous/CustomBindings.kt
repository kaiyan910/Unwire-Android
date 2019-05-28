package hk.olleh.unwire.common.miscellaneous

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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

    @BindingAdapter("html")
    @JvmStatic
    fun loadHtml(view: WebView, html: String?) {

        html?.let {

            val data = """
                <html>
                    <head>
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                        <style>img{max-width: 100%; width:auto; height: auto;}</style>
                    </head>
                    <body>$html</body>
                </html>
            """.trimIndent()

            view.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null)
        }
    }
}