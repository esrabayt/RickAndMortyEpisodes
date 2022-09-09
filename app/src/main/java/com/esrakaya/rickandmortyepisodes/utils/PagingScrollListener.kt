package com.esrakaya.rickandmortyepisodes.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PagingScrollListener(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    abstract val lockLoadMore: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (totalItemCount < previousTotal) {
                previousTotal = totalItemCount
                if (totalItemCount == 0) {
                    loading = true
                }
            }

            if (loading && totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }

            if (canLoadMore()) {
                loadMoreItems()
                loading = true
            }
        }
    }

    protected abstract fun loadMoreItems()

    private fun canLoadMore(): Boolean {
        return lockLoadMore.not() &&
                loading.not() &&
                totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 6
    }
}
