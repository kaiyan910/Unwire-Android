package hk.olleh.unwire.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hk.olleh.unwire.common.base.BaseViewModel
import hk.olleh.unwire.common.miscellaneous.ErrorState
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.useCase.SearchPostUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class PostSearchViewModel(
    val keyword: String,
    private val searchPostUseCase: SearchPostUseCase
) : BaseViewModel() {

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>>> get() = _posts

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private var page = 1
    private var canLoadMode = true

    init {

        _loading.value = false
        querySearchResult()
    }

    fun loadMore() {
        if (canLoadMode) {
            page++
            querySearchResult()
        }
    }

    private fun querySearchResult() = viewModelScope
        .launch {

            try {

                // show loading
                _loading.postValue(true)

                val posts = searchPostUseCase.getPosts("", page, keyword)

                if (posts.isEmpty()) {
                    canLoadMode = false
                }

                val newList = when (_posts.value) {
                    is Resource.Success -> (_posts.value as Resource.Success).data.toMutableList()
                        .apply { addAll(posts) }
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