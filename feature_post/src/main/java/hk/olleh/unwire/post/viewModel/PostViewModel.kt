package hk.olleh.unwire.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import hk.olleh.unwire.common.base.BaseViewModel
import hk.olleh.unwire.common.miscellaneous.ErrorState
import hk.olleh.unwire.common.miscellaneous.Resource
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.useCase.GetPostUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class PostViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val category: String
): BaseViewModel() {

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>>> get() = _posts

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private var page = 1
    private var canLoadMode = true

    init {
        getPosts(category, page)
    }

    fun loadMore() {
        if (canLoadMode) {
            page++
            getPosts(category, page)
        }
    }

    private fun getPosts(category: String, page: Int) = viewModelScope
        .launch {

            try {

                // show loading
                _loading.postValue(true)

                // when page == 1 means a new category is fetched, so remove the old data first
                /*if (page == 1) {
                    _posts.postValue(Resource.Success(listOf()))
                }*/

                val posts = getPostUseCase.getPosts(category, page)

                if (posts.isEmpty()) {
                    canLoadMode = false
                }

                val newList = when(_posts.value) {
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