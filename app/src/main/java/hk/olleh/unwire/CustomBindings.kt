package hk.olleh.unwire

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

object CustomBindings {

    @BindingAdapter("onNavigationItemSelected")
    @JvmStatic
    fun setOnNavigationItemSelectedListener(
        view: BottomNavigationView,
        listener: BottomNavigationView.OnNavigationItemSelectedListener
    ) {
        view.setOnNavigationItemSelectedListener(listener)
    }
}