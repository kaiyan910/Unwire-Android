package hk.olleh.unwire.post.ui

import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.miscellaneous.EndlessScrollingListener
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostBookmarkBinding
import hk.olleh.unwire.post.viewModel.PostBookmarkViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class PostBookmarkFragment : BaseFragment<FragmentPostBookmarkBinding>() {

    private val viewModel: PostBookmarkViewModel
            by viewModel()

    private val postListAdapter: PostListAdapter
            by inject()

    private lateinit var searchView: SearchView

    override fun layout(): Int = R.layout.fragment_post_bookmark

    override fun afterViews() {
        super.afterViews()
        bindings
            ?.apply {
                // set the view model
                vm = viewModel

                postListAdapter
                    .apply {
                        onItemClickListener = {

                            val bundle = bundleOf("post" to it)

                            findNavController()
                                .navigate(
                                    R.id.action_postBookmarkFragment_to_postDetailsFragment,
                                    bundle
                                )
                        }
                    }

                swipeRefreshLayout.setColorSchemeResources(R.color.theme_color)

                toolbar
                    .apply {

                        // setup the menu
                        inflateMenu(R.menu.menu_post)

                        // grab the search view for reference
                        searchView = menu
                            .findItem(R.id.menu_search)
                            .actionView as SearchView

                        setupSearchView(searchView)

                        // setup the on menu click listener
                        setOnMenuItemClickListener {
                            searchView.onActionViewExpanded()
                            false
                        }
                    }

                // set the recycler view
                rvPost
                    .apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = postListAdapter

                        addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

                        // set on scroll listener
                        addOnScrollListener(object : EndlessScrollingListener() {
                            override fun onLoadMore() {
                                viewModel.loadMore()
                            }
                        })
                    }
            }
    }

    override fun observe() {
        super.observe()
        viewModel
            .posts
            .observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Success -> postListAdapter.submitList(it.data)
                    is Resource.Error -> {
                    }
                }
            })
    }

    private fun setupSearchView(searchView: SearchView) {

        searchView
            .apply {

                setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(text: String): Boolean {

                        viewModel.getBookmark(text, true)
                        return false
                    }

                    override fun onQueryTextChange(text: String): Boolean {

                        if (text.isEmpty()) {
                            viewModel.getBookmark("", true)
                        }

                        return false
                    }
                })
            }

    }
}