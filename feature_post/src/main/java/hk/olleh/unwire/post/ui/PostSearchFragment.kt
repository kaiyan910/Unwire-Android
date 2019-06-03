package hk.olleh.unwire.post.ui

import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.miscellaneous.EndlessScrollingListener
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostSearchBinding
import hk.olleh.unwire.post.viewModel.PostSearchViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PostSearchFragment : BaseFragment<FragmentPostSearchBinding>() {

    private val viewModel by viewModel<PostSearchViewModel>()

    private val postListAdapter: PostListAdapter by inject()

    override fun layout(): Int = R.layout.fragment_post_search

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
                                .navigate(R.id.action_postSelectionFragment_to_postDetailsFragment, bundle)
                        }
                    }

                swipeRefreshLayout.setColorSchemeResources(R.color.theme_color)

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
}