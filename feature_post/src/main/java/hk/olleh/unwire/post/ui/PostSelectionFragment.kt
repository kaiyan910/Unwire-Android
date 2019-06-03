package hk.olleh.unwire.post.ui

import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostSelectionBinding
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf

class PostSelectionFragment : BaseFragment<FragmentPostSelectionBinding>() {

    private val pagerAdapter by currentScope.inject<PostSelectionPagerAdapter> { parametersOf(childFragmentManager) }

    override fun layout(): Int = R.layout.fragment_post_selection

    override fun afterViews() {
        super.afterViews()
        bindings
            ?.apply {
                viewPager.adapter = pagerAdapter

                toolbar
                    .apply {
                        inflateMenu(R.menu.menu_post)
                        setOnMenuItemClickListener {
                            true
                        }
                    }

                tabLayout.setupWithViewPager(viewPager)
            }
    }
}