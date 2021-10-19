package miu.compro.cs743.myapplication.module

import kotlinx.coroutines.Dispatchers
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailViewModel
import miu.compro.cs743.myapplication.ui.fragments.search.SearchViewModel
import miu.compro.cs743.myapplication.ui.fragments.home.HomeViewModel
import miu.compro.cs743.myapplication.ui.activity.main.MainViewModel
import miu.compro.cs743.myapplication.ui.fragments.login.LoginViewModel
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListViewModel
import miu.compro.cs743.myapplication.ui.fragments.video.VideoViewModel
import miu.compro.cs743.myapplication.ui.fragments.profile.ProfileViewModel
import miu.compro.cs743.myapplication.ui.fragments.register.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    factory { Dispatchers.IO }
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { VideoViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { NewsListViewModel(get(), get(), get()) }
    viewModel { ArticleDetailViewModel() }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
}