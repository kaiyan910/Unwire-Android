package hk.olleh.unwire.common.miscellaneous

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout

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

    @BindingAdapter("selectedTab")
    @JvmStatic
    fun setSelectedTab(view: TabLayout, position: Int?) {

        position?.let {

            if (view.selectedTabPosition != position) {
                view
                    .getTabAt(position)
                    ?.select()
            }
        }
    }

    @InverseBindingAdapter(attribute = "selectedTab")
    @JvmStatic
    fun getSelectedTab(view: TabLayout): Int = view.selectedTabPosition

    @BindingAdapter("app:selectedTabAttrChanged")
    @JvmStatic fun setSelectedTabListeners(
        view: TabLayout,
        attrChange: InverseBindingListener
    ) {

        view.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {

                attrChange.onChange()
            }
        })
    }
}