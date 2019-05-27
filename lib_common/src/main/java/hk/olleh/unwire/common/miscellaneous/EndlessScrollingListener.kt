package hk.olleh.unwire.common.miscellaneous

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollingListener : RecyclerView.OnScrollListener() {

    private val mVisibleThreshold = 5
    private var mPreviousTotal = 0
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)

        val itemCountAsVisible = recyclerView.childCount
        val itemCountAsAll = recyclerView.layoutManager
            ?.itemCount
            ?: 0

        val firstVisibleItem = when (recyclerView.layoutManager) {

            is LinearLayoutManager -> (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            is GridLayoutManager -> (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            else -> 0
        }

        if (mLoading) {
            if (itemCountAsAll > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = itemCountAsAll
            }
        }

        if (!mLoading && itemCountAsAll - itemCountAsVisible <= firstVisibleItem + mVisibleThreshold) {
            onLoadMore()
            mLoading = true
        }
    }

    abstract fun onLoadMore()
}