package hk.olleh.unwire.post.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PostSelectionPagerAdapter(
    manager: FragmentManager,
    private val titles: Array<String>
) : FragmentPagerAdapter(manager) {

    companion object {

        const val TAB_NEWS = ""
        const val TAB_MOBILE = "5"
        const val TAB_NOTEBOOK = "7"
        const val TAB_AV = "3481,6394,35"
        const val TAB_REVIEW = "7311"
        const val TAB_APPS = "4909,1959,1961,1962"
    }

    override fun getItem(position: Int): Fragment {

        val category = when(position) {
            1 -> TAB_MOBILE
            2 -> TAB_NOTEBOOK
            3 -> TAB_AV
            4 -> TAB_REVIEW
            5 -> TAB_APPS
            else -> TAB_NEWS
        }

        return PostFragment.create(category)
    }

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}