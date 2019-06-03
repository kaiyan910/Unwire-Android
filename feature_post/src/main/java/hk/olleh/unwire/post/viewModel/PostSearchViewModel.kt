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
    private val searchPostUseCase: SearchPostUseCase
) : BaseViewModel() {

    val keyword: MutableLiveData<String> = MutableLiveData()

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>>> get() = _posts

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private var page = 1
    private var canLoadMode = true
    private var searhingKeyword = ""

    init {

        _loading.value = false
    }

    fun loadMore() {
        if (canLoadMode) {
            page++
            Timber.d("[DEBUG] =123 called loadMore()")
            querySearchResult()
        }
    }

    fun onClearKeyword() {
        keyword.value = ""
        page = 1
        searhingKeyword = ""
        _posts.value = Resource.Success(listOf())
    }

    fun onSearch() {
        page = 1
        _posts.value = Resource.Success(listOf())
        if (!keyword.value.isNullOrEmpty()) {
            searhingKeyword = keyword.value!!
            Timber.d("[DEBUG] =123 called onSearch()")
            querySearchResult()
        }
    }

    private fun querySearchResult() = viewModelScope
        .launch {

            try {

                Timber.d("[DEBUG] =123 called querySearchResult()")
                // show loading
                _loading.postValue(true)

                val posts = searchPostUseCase.getPosts("", page, searhingKeyword)

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