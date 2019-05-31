package hk.olleh.unwire.post.ui

import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import hk.olleh.unwire.common.extras
import hk.olleh.unwire.common.miscellaneous.VideoFullscreenWebChromeClient
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.ActivityPostVideoBinding

class PostVideoActivity : AppCompatActivity() {

    val item: Post by extras("post")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil
            .setContentView<ActivityPostVideoBinding>(this, R.layout.activity_post_video)
            .apply {
                // set the lifecycle owner
                lifecycleOwner = this@PostVideoActivity
                // set model class to XML
                post = item
                // setup toolbar
                toolbar
                    .apply {
                        setNavigationOnClickListener { onBackPressed() }
                    }
                // setup web view
                webView
                    .apply {

                        webChromeClient = VideoFullscreenWebChromeClient(this@PostVideoActivity, flFullscreen)
                        settings
                            .apply {
                                //mediaPlaybackRequiresUserGesture = false
                                javaScriptEnabled = true
                            }

                        loadUrl(item.video?.replace("www", "m"))
                    }
            }
    }
}