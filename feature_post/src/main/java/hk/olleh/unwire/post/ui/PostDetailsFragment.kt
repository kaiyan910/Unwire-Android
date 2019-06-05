package hk.olleh.unwire.post.ui

import android.content.Intent
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import hk.olleh.unwire.common.argument
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostDetailsBinding
import hk.olleh.unwire.post.viewModel.PostDetailsViewModel
import hk.olleh.unwire.preferences.PreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>() {

    private val details
            by argument<Post>("post")

    private val preferencesRepository: PreferencesRepository
            by inject()

    private val viewModel
            by viewModel<PostDetailsViewModel> { parametersOf(details) }

    private lateinit var bookmark: MenuItem

    override fun layout(): Int = R.layout.fragment_post_details

    override fun afterViews() {
        super.afterViews()
        bindings
            ?.apply {

                // setup the mode for XML
                dark = preferencesRepository.isDarkMode()

                // change refresh layout color scheme
                swipeRefreshLayout.setColorSchemeResources(R.color.theme_color)

                toolbar
                    .apply {

                        // setup navigation
                        setNavigationOnClickListener { findNavController().popBackStack() }
                        // setup menu
                        inflateMenu(R.menu.menu_post_details)
                        // grab the bookmark menu item for later use
                        bookmark = menu.findItem(R.id.menu_bookmark)
                        // setup on menu item click listener
                        setOnMenuItemClickListener {

                            when (it.itemId) {

                                R.id.menu_share -> {

                                    Intent(Intent.ACTION_SEND)
                                        .apply {
                                            putExtra(Intent.EXTRA_TEXT, "${details.link}\n${details.title}")
                                            type = "text/plain"
                                            startActivity(this)
                                        }
                                }

                                R.id.menu_bookmark -> {

                                    // toggle bookmark
                                    viewModel.toggleBookmark()
                                }
                            }

                            true
                        }
                    }
            }

        lifecycleScope
            .launch {

                delay(800)
                bindings?.post = details
            }
    }

    override fun observe() {
        super.observe()
        viewModel
            .bookmark
            .observe(this, Observer { isBookmark ->

                context
                    ?.let { context ->

                        val attrs = if (isBookmark) {
                            intArrayOf(R.attr.appToolbarBookmark)
                        } else {
                            intArrayOf(R.attr.appToolbarBookmarkOutline)
                        }

                        with(context.theme.obtainStyledAttributes(attrs)) {
                            val resourceId = getResourceId(0, 0)
                            bookmark.icon = ContextCompat.getDrawable(context, resourceId)
                            recycle()
                        }
                    }
            })
    }
}