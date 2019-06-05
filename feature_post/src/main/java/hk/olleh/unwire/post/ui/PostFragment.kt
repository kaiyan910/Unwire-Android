package hk.olleh.unwire.post.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.widget.LinearLayout.VERTICAL
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hk.olleh.unwire.common.Constant
import hk.olleh.unwire.common.argument
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.miscellaneous.EndlessScrollingListener
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostBinding
import hk.olleh.unwire.post.viewModel.PostViewModel
import org.koin.android.ext.android.inject
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

    private val category
            by argument(ARGS_CATEGORY, "")

    private val isPro
            by argument(ARGS_PRO, false)

    private val viewModel
            by viewModel<PostViewModel> { parametersOf(category, isPro) }

    private val postListAdapter: PostListAdapter
            by inject()

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

                            if (category == Constant.CATEGORY_TV) {

                                activity?.apply {

                                    val intent = Intent(this, PostVideoActivity::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                                }

                            } else {
                                findNavController()
                                    .navigate(
                                        R.id.action_postSelectionFragment_to_postDetailsFragment,
                                        bundle
                                    )
                            }
                        }
                    }

                swipeRefreshLayout.setColorSchemeResources(R.color.theme_color)

                // set the recycler view
                rvPost
                    .apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = postListAdapter

                        addItemDecoration(DividerItemDecoration(context, VERTICAL))

                        // set on scroll listener
                        addOnScrollListener(object : EndlessScrollingListener() {
                            override fun onLoadMore() {
                                viewModel.loadMore()
                            }
                        })
                    }
            }
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator {

        // Fix for disappear while parent fragment is in transition
        val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1F, 1F)
        //time same with parent fragment's animation
        objectAnimator.duration = 333
        return objectAnimator
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