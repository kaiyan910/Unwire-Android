package hk.olleh.unwire.post.ui

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hk.olleh.unwire.common.argument
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.miscellaneous.EndlessScrollingListener
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostBinding
import hk.olleh.unwire.post.viewModel.PostViewModel
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostFragment : BaseFragment<FragmentPostBinding>() {

    companion object {

        const val ARGS_CATEGORY = "args_category"
        const val ARGS_PRO = "args_pro"

        fun create(category: String, isPro: Boolean = false) = PostFragment()
            .apply {
                arguments = bundleOf(
                    ARGS_CATEGORY to category,
                    ARGS_PRO to isPro
                )
            }
    }

    private val category by argument(ARGS_CATEGORY, "")
    private val isPro by argument(ARGS_PRO, false)

    private val viewModel by viewModel<PostViewModel> { parametersOf(category, isPro) }

    private val postListAdapter: PostListAdapter by currentScope.inject()

    override fun layout(): Int = R.layout.fragment_post

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
                            findNavController().navigate(R.id.action_postSelectionFragment_to_postDetailsFragment, bundle)
                        }
                    }

                swipeRefreshLayout.setColorSchemeResources(R.color.theme_color)

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