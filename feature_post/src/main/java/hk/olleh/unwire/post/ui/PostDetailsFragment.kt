package hk.olleh.unwire.post.ui

import androidx.navigation.fragment.findNavController
import hk.olleh.unwire.common.argument
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>() {

    private val details by argument<Post>("post")

    override fun layout(): Int = R.layout.fragment_post_details

    override fun afterViews() {
        super.afterViews()
        bindings
            ?.apply {
                post = details
                toolbar
                    .apply {
                        setNavigationIcon(R.drawable.ic_back)
                        setNavigationOnClickListener { findNavController().popBackStack() }
                    }
            }
    }
}