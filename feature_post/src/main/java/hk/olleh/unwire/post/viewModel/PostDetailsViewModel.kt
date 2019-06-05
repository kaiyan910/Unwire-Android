package hk.olleh.unwire.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hk.olleh.unwire.common.base.BaseViewModel
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.common.repository.DatabaseRepository
import hk.olleh.unwire.post.useCase.BookmarkPostUseCase
import hk.olleh.unwire.post.useCase.CheckPostBookmarkUseCase
import hk.olleh.unwire.post.useCase.SearchPostUseCase
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val post: Post,
    private val bookmarkPostUseCase: BookmarkPostUseCase,
    private val checkPostBookmarkUseCase: CheckPostBookmarkUseCase
) : BaseViewModel() {

    private val _bookmark: MutableLiveData<Boolean> = MutableLiveData()
    val bookmark: LiveData<Boolean> = _bookmark

    init {

        initBookmark()
    }

    fun toggleBookmark() {

        viewModelScope.launch {
            // update the database
            bookmarkPostUseCase.bookmark(post, !_bookmark.value!!)
            // update the bookmark field to for updating UI
            _bookmark.value = !_bookmark.value!!
        }
    }

    private fun initBookmark() {

        viewModelScope.launch {
            _bookmark.value = checkPostBookmarkUseCase.isPostBookmarked(post.id)
        }
    }
}