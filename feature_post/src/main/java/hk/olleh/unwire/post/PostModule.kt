package hk.olleh.unwire.post

import androidx.fragment.app.FragmentManager
import hk.olleh.unwire.common.model.Post
import hk.olleh.unwire.post.ui.PostListAdapter
import hk.olleh.unwire.post.ui.PostSelectionFragment
import hk.olleh.unwire.post.ui.PostSelectionPagerAdapter
import hk.olleh.unwire.post.useCase.BookmarkPostUseCase
import hk.olleh.unwire.post.useCase.BookmarkPostUseCaseImpl
import hk.olleh.unwire.post.useCase.CheckPostBookmarkUseCase
import hk.olleh.unwire.post.useCase.CheckPostBookmarkUseCaseImpl
import hk.olleh.unwire.post.useCase.GetBookmarkPostUseCase
import hk.olleh.unwire.post.useCase.GetBookmarkPostUseCaseImpl
import hk.olleh.unwire.post.useCase.GetPostBySlugUseCase
import hk.olleh.unwire.post.useCase.GetPostBySlugUseCaseImpl
import hk.olleh.unwire.post.useCase.GetPostUseCase
import hk.olleh.unwire.post.useCase.GetPostUseCaseImpl
import hk.olleh.unwire.post.useCase.SearchPostUseCase
import hk.olleh.unwire.post.useCase.SearchPostUseCaseImpl
import hk.olleh.unwire.post.viewModel.PostBookmarkViewModel
import hk.olleh.unwire.post.viewModel.PostDetailsViewModel
import hk.olleh.unwire.post.viewModel.PostSearchViewModel
import hk.olleh.unwire.post.viewModel.PostViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val postModule = module {

    factory<GetPostUseCase> { GetPostUseCaseImpl(get()) }

    factory<SearchPostUseCase> { SearchPostUseCaseImpl(get()) }

    factory<CheckPostBookmarkUseCase> { CheckPostBookmarkUseCaseImpl(get()) }

    factory<BookmarkPostUseCase> { BookmarkPostUseCaseImpl(get()) }

    factory<GetBookmarkPostUseCase> { GetBookmarkPostUseCaseImpl(get()) }

    factory<GetPostBySlugUseCase> { GetPostBySlugUseCaseImpl(get()) }

    factory { PostListAdapter() }

    viewModel { (post: Post?, slug: String?) -> PostDetailsViewModel(post, slug, get(), get(), get()) }

    viewModel { (category: String, isPro: Boolean) -> PostViewModel(get(), category, isPro) }

    viewModel { (keyword: String) -> PostSearchViewModel(keyword, get()) }

    viewModel { PostBookmarkViewModel(get()) }

    scope(named<PostSelectionFragment>()) {

        scoped(named("POST_SELECTION_TABS_TITLE")) {

            androidContext()
                .resources
                .getStringArray(R.array.post_tabs)
        }

        scoped { (manager: FragmentManager) ->
            PostSelectionPagerAdapter(manager, get(named("POST_SELECTION_TABS_TITLE")))
        }
    }
}