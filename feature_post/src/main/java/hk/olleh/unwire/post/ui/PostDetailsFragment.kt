package hk.olleh.unwire.post.ui

import androidx.navigation.fragment.findNavController
import hk.olleh.unwire.common.argument
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostDetailsBinding
import hk.olleh.unwire.preferences.PreferencesRepository
import org.koin.android.ext.android.inject

class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>() {

    private val details by argument<Post>("post")

    private val preferencesRepository: PreferencesRepository by inject()

    override fun layout(): Int = R.layout.fragment_post_details

    override fun afterViews() {
        super.afterViews()
        bindings
            ?.apply {
                post = details
                dark = preferencesRepository.isDarkMode()
                toolbar
                    .apply {
                        setNavigationOnClickListener { findNavController().popBackStack() }
                    }
            }
    }
}