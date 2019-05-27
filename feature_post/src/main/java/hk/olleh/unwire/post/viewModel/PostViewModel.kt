package hk.olleh.unwire.post.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    var category: Int
) : BaseViewModel() {

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>>> get() = _posts

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private var page = 0

    fun loadMore() {
        page++
        getPosts(category, page)
    }

    fun changeCategory(newCategory: Int) {
        category = newCategory
        page = 0
        getPosts(category, page)
    }

    private fun getPosts(category: Int, page: Int) = viewModelScope
        .launch {

            try {
                // show loading
                _loading.postValue(true)

                val posts = getPostUseCase.getPosts(category, page)
                _posts.postValue(Resource.success(posts))

            } catch (e: Exception) {

                Timber.e(e)
                _posts.postValue(Resource.error(ErrorState("")))

            } finally {

                // hide loading
                _loading.postValue(false)
            }
        }
}