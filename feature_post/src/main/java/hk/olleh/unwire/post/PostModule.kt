package hk.olleh.unwire.post

import hk.olleh.unwire.post.ui.PostFragment
import hk.olleh.unwire.post.ui.PostListAdapter
import hk.olleh.unwire.post.useCase.GetPostUseCase
import hk.olleh.unwire.post.useCase.GetPostUseCaseImpl
import hk.olleh.unwire.post.viewModel.PostViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val postModule = module {

    factory<GetPostUseCase> { GetPostUseCaseImpl(get()) }

    viewModel { PostViewModel(get()) }

    scope(named<PostFragment>()) {

        scoped { PostListAdapter() }
    }
}