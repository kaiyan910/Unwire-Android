package hk.olleh.unwire.post.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hk.olleh.unwire.common.extras
import hk.olleh.unwire.common.miscellaneous.VideoFullscreenWebChromeClient
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.ActivityPostVideoBinding
import timber.log.Timber

class PostVideoActivity : AppCompatActivity() {

    private val item: Post
            by extras("post")

    private lateinit var chromeClient: VideoFullscreenWebChromeClient

    private lateinit var bindings: ActivityPostVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindings = DataBindingUtil
            .setContentView<ActivityPostVideoBinding>(this, R.layout.activity_post_video)
            .apply {
                // set the lifecycle owner
                lifecycleOwner = this@PostVideoActivity
                // set model class to XML
                post = item
            }

        // setup toolbar
        bindings
            .toolbar
            .apply {
                setNavigationOnClickListener { onBackPressed() }
                inflateMenu(R.menu.menu_post_details)
                setOnMenuItemClickListener {

                    Intent(Intent.ACTION_SEND)
                        .apply {
                            putExtra(Intent.EXTRA_TEXT, "${item.link}\n${item.title}")
                            type = "text/plain"
                            startActivity(this)
                        }

                    true
                }
            }

        // setup web view
        bindings
            .webView
            .apply {
                Timber.d("[DEBUG] setup chrome client.")

                chromeClient = VideoFullscreenWebChromeClient(this@PostVideoActivity, bindings.flFullscreen)
                // assign chrome client to web view
                webChromeClient = chromeClient
                // apply settings
                settings
                    .apply {
                        //mediaPlaybackRequiresUserGesture = false
                        javaScriptEnabled = true
                    }

                val videoUrl = item.video?.replace("www", "m")
                Timber.d("[DEBUG] load video $videoUrl")
                // load the video url
                loadUrl(videoUrl)
            }
    }

    override fun onBackPressed() {

        if (chromeClient.underCustomViewMode()) {
            chromeClient.onHideCustomView()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {

        bindings
            .webView
            .saveState(outState)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        bindings
            .webView
            .restoreState(savedInstanceState)
    }
}