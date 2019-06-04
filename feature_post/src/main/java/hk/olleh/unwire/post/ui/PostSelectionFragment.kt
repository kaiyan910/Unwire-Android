package hk.olleh.unwire.post.ui

import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import hk.olleh.unwire.common.base.BaseFragment
import hk.olleh.unwire.post.R
import hk.olleh.unwire.post.databinding.FragmentPostSelectionBinding
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class PostSelectionFragment : BaseFragment<FragmentPostSelectionBinding>() {

    private val pagerAdapter by currentScope.inject<PostSelectionPagerAdapter> { parametersOf(childFragmentManager) }

    private lateinit var searchView: SearchView

    override fun layout(): Int = R.layout.fragment_post_selection

    override fun afterViews() {
        super.afterViews()
        bindings
            ?.apply {
                viewPager.adapter = pagerAdapter

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

                tabLayout.setupWithViewPager(viewPager)
            }
    }

    private fun setupSearchView(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                val bundle = bundleOf("keyword" to query)
                findNavController()
                    .navigate(R.id.action_postSelectionFragment_to_postSearchFragment, bundle)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
}