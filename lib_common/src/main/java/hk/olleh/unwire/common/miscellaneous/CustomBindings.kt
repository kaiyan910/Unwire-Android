package hk.olleh.unwire.common.miscellaneous

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CustomBindings {

    @JvmStatic
    @BindingAdapter("remote")
    fun loadRemoteSource(view: ImageView, src: String?) {

        src.let {

            Glide
                .with(view.context)
                .load(it)
                .into(view)
        }
    }
}