package hk.olleh.unwire.post.viewModel

import hk.olleh.unwire.common.base.BaseViewModel
import hk.olleh.unwire.post.useCase.SearchPostUseCase

class PostDetailsViewModel(
    val keyword: String,
    private val searchPostUseCase: SearchPostUseCase
) : BaseViewModel() {

}