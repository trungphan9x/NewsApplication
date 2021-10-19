package miu.compro.cs743.myapplication.injection

import miu.compro.cs743.myapplication.NewsApplication.Companion.INSTANCE
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.util.NewsAppSharedPreference
import org.koin.dsl.module

val utilModule = module {
    factory { NewsAppSharedPreference(applicationContext()) }
}