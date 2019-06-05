package hk.olleh.unwire.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hk.olleh.unwire.common.base.BaseViewModel
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.useCase.BookmarkPostUseCase
import hk.olleh.unwire.post.useCase.CheckPostBookmarkUseCase
import hk.olleh.unwire.post.useCase.GetPostBySlugUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val initial: Post?,
    private val slug: String?,
    private val bookmarkPostUseCase: BookmarkPostUseCase,
    private val checkPostBookmarkUseCase: CheckPostBookmarkUseCase,
    private val getPostBySlugUseCase: GetPostBySlugUseCase
) : BaseViewModel() {

    private val _bookmark: MutableLiveData<Boolean> = MutableLiveData()
    val bookmark: LiveData<Boolean> = _bookmark

    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post> = _post

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _share: MutableLiveData<Post> = MutableLiveData()
    val share: LiveData<Post> = _share

    init {

        when {

            initial != null -> delayInitialSetup()
            // call api to retrieve the post by slug
            slug != null -> getPostDetailsBySlug()
        }
    }

    fun share() {

        _post
            .value
            ?.let { _share.value = it }
    }

    fun toggleBookmark() = viewModelScope
        .launch {

            if (_post.value != null && _bookmark.value != null) {

                // update the database
                bookmarkPostUseCase.bookmark(
                    _post.value!!,
                    !_bookmark.value!!
                )

                // update the bookmark field to for updating UI
                _bookmark.value = !_bookmark.value!!
            }
        }

    private fun delayInitialSetup() = viewModelScope
        .launch {

            _loading.value = true

            // delay some time for better web view UX
            delay(800)
            // set the post from initial value
            _post.value = initial
            // post must not null check the bookmark status
            initBookmark()

            _loading.value = false
        }


    private fun getPostDetailsBySlug() = viewModelScope
        .launch {

            _loading.value = true

            // call api to get the post by slug
            val retrieved = getPostBySlugUseCase.getPostBySlug(slug!!)
            // set post
            _post.value = retrieved
            // post must not null check the bookmark status
            initBookmark()

            _loading.value = false
        }


    private fun initBookmark() = viewModelScope
        .launch {

            _post
                .value
                ?.let {
                    _bookmark.value = checkPostBookmarkUseCase.isPostBookmarked(it.id)
                }
        }
}