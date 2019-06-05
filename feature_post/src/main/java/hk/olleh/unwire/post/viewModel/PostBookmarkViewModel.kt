package hk.olleh.unwire.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hk.olleh.unwire.common.base.BaseViewModel
import hk.olleh.unwire.common.miscellaneous.ErrorState
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.useCase.GetBookmarkPostUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class PostBookmarkViewModel(
    private val getBookmarkPostUseCase: GetBookmarkPostUseCase
) : BaseViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>>> = _posts

    private var currentKeyword = ""
    private var page = 0
    private var canLoadMode = true

    init {

        getBookmark(currentKeyword, false)
    }

    fun loadMore() {
        if (canLoadMode) {
            page++
            getBookmark(currentKeyword, false)
        }
    }

    fun getBookmark(keyword: String, resetPage: Boolean) = viewModelScope
        .launch {

            try {

                // show loading
                _loading.postValue(true)

                if (resetPage) {
                    currentKeyword = keyword
                    page = 0
                }

                val posts = getBookmarkPostUseCase.getBookmarkPost(page, keyword)

                if (posts.isEmpty()) {
                    canLoadMode = false
                }

                val newList = when (_posts.value) {
                    is Resource.Success -> (_posts.value as Resource.Success).data.toMutableList()
                        .apply {
                            if (resetPage) {
                                clear()
                            }
                            addAll(posts)
                        }
                        .toList()

                    else -> posts
                }

                _posts.postValue(Resource.Success(newList))

            } catch (e: Exception) {

                Timber.e(e)
                _posts.postValue(Resource.Error(ErrorState("")))

            } finally {

                // hide loading
                _loading.postValue(false)
            }
        }
}