package hk.olleh.unwire.post.ui

import android.content.Intent
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import hk.olleh.unwire.common.argumentOrNull
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostDetailsBinding
import hk.olleh.unwire.post.viewModel.PostDetailsViewModel
import hk.olleh.unwire.preferences.PreferencesRepository
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>() {

    private val details
            by argumentOrNull<Post>("post")

    private val slug
            by argumentOrNull<String>("slug")

    private val preferencesRepository: PreferencesRepository
            by inject()

    private val viewModel
            by viewModel<PostDetailsViewModel> { parametersOf(details, slug) }

    private lateinit var bookmark: MenuItem

    override fun layout(): Int = R.layout.fragment_post_details

    override fun afterViews() {
        super.afterViews()
        bindings
            ?.apply {

                vm = viewModel

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
                                R.id.menu_share -> viewModel.share()
                                R.id.menu_bookmark -> viewModel.toggleBookmark()
                            }

                            true
                        }
                    }
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

        viewModel
            .share
            .observe(this, Observer { post ->

                Intent(Intent.ACTION_SEND)
                    .apply {
                        putExtra(Intent.EXTRA_TEXT, "${post.link}\n${post.title}")
                        type = "text/plain"
                        startActivity(this)
                    }
            })
    }
}