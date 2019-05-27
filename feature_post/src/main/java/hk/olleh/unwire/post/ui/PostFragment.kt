package hk.olleh.unwire.post.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.miscellaneous.EndlessScrollingListener
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostBinding
import hk.olleh.unwire.post.viewModel.PostViewModel
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class PostFragment : BaseFragment<FragmentPostBinding>() {

    private val viewModel: PostViewModel by viewModel()
    private val postListAdapter: PostListAdapter by currentScope.inject()

    override fun layout(): Int = R.layout.fragment_post

    override fun afterViews() {
        super.afterViews()

        bindings
            ?.apply {
                // set the view model
                vm = viewModel
                // set the recycler view
                rvPost
                    .apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = postListAdapter

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