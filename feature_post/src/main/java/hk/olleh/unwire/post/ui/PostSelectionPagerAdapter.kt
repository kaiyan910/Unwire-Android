package hk.olleh.unwire.post.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hk.olleh.unwire.common.Constant

class PostSelectionPagerAdapter(
    manager: FragmentManager,
    private val titles: Array<String>
) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {

        val category = when(position) {
            1 -> Constant.CATEGORY_TV
            2 -> Constant.CATEGORY_MOBILE
            3 -> Constant.CATEGORY_NOTEBOOK
            4 -> Constant.CATEGORY_AV
            5 -> Constant.CATEGORY_REVIEW
            6 -> Constant.CATEGORY_APPS
            7 -> Constant.CATEGORY_PRO
            8 -> Constant.CATEGORY_PRO_SECURITY
            9 -> Constant.CATEGORY_PRO_INTERVIEW
            else -> Constant.CATEGORY_NEWS
        }

        return PostFragment.create(category, position >= 7)
    }

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}