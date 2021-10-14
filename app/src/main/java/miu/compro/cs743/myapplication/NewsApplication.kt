package miu.compro.cs743.myapplication

import android.app.Application
import miu.compro.cs743.myapplication.injection.apiModule
import miu.compro.cs743.myapplication.injection.networkModule
import miu.compro.cs743.myapplication.injection.repositoryModule
import miu.compro.cs743.myapplication.module.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@NewsApplication)
            modules(listOf(viewModelsModule, networkModule, repositoryModule, apiModule))
        }
    }
}