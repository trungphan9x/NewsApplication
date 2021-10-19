package miu.compro.cs743.myapplication

import android.app.Application
import android.content.Context
import miu.compro.cs743.myapplication.injection.*
import miu.compro.cs743.myapplication.module.viewModelsModule
import miu.compro.cs743.myapplication.util.NewsAppSharedPreference
import okhttp3.internal.platform.android.AndroidSocketAdapter.Companion.factory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    init {
        INSTANCE = this
    }
    override fun onCreate() {
        super.onCreate()
//        val context: Context = applicationContext()

        startKoin{
            androidLogger()
            androidContext(this@NewsApplication)
            modules(listOf(viewModelsModule, networkModule, repositoryModule, apiModule, roomModule, utilModule))
        }
    }

    companion object {
        var INSTANCE: NewsApplication? = null

        fun applicationContext() : Context {
            return INSTANCE!!.applicationContext
        }
    }
}