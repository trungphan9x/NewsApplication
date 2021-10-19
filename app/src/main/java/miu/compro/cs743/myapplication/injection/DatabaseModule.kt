package miu.compro.cs743.myapplication.injection

import miu.compro.cs743.myapplication.model.roomdb.NewsAppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val roomModule = module {
    single {
        NewsAppDatabase(androidApplication())
    }
    single(createdAtStart = false) { get<NewsAppDatabase>().userDao() }
    single(createdAtStart = false) { get<NewsAppDatabase>().bookmarkDao() }
}