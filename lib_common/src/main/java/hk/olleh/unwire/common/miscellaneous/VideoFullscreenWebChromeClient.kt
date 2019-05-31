package hk.olleh.unwire.common.miscellaneous

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Message
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import timber.log.Timber
import java.lang.ref.WeakReference

class VideoFullscreenWebChromeClient(
    activity: Activity,
    containerViewGroup: ViewGroup
) : WebChromeClient() {

    private val fullscreenLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

    private val activityReference = WeakReference<Activity>(activity)
    private val container = WeakReference<ViewGroup>(containerViewGroup)
    private var customView: View? = null

    private var savedSystemUiVisibility: Int = 0

    override fun onHideCustomView() {

        Timber.d("[DEBUG] onHideCustomView")

        restoreUi()

        container
            .get()
            ?.removeAllViews()
    }

    override fun onCloseWindow(webview: WebView?) {

        Timber.d("[DEBUG] onCloseWindow")

        restoreUi()

        container.clear()
        activityReference.clear()
    }

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {

        Timber.d("[DEBUG] onCreateWindow isDialog=$isDialog isUserGesture=$isUserGesture")

        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {

        Timber.d("[DEBUG] onShowCustomView")

        view
            ?.apply {

                customView = view
                layoutParams = fullscreenLayoutParams

                activityReference
                    .get()
                    ?.apply {

                        // save current ui visibility
                        savedSystemUiVisibility = window
                            .decorView
                            .systemUiVisibility
                        // make it full screen
                        window
                            .decorView
                            .systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
                        // change to landscape mode
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }

                container
                    .get()
                    ?.apply {

                        visibility = View.VISIBLE
                        addView(view)
                    }
            }
    }

    private fun restoreUi() {

        customView = null

        container
            .get()
            ?.visibility = View.GONE

        activityReference
            .get()
            ?.apply {

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                // restore status bar
                window
                    .decorView
                    .systemUiVisibility = savedSystemUiVisibility
            }
    }
}