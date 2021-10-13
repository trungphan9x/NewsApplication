package miu.compro.cs743.myapplication.module

import miu.compro.cs743.myapplication.ui.search.SearchViewModel
import miu.compro.cs743.myapplication.ui.home.HomeViewModel
import miu.compro.cs743.myapplication.ui.main.MainViewModel
import miu.compro.cs743.myapplication.ui.video.VideoViewModel
import miu.compro.cs743.myapplication.ui.profile.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { SearchViewModel() }
    viewModel { VideoViewModel() }
    viewModel { ProfileViewModel() }
}