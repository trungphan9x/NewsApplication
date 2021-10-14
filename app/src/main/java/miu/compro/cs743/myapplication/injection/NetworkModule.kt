package miu.compro.cs743.myapplication.injection

import com.google.gson.GsonBuilder
import miu.compro.cs743.myapplication.BuildConfig
import miu.compro.cs743.myapplication.model.Constant
import miu.compro.cs743.myapplication.util.ResponseHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    factory { buildOkHttpClient() }
    single { buildRetrofit(get()) }
    factory { ResponseHandler() }
}


fun buildOkHttpClient() = OkHttpClient.Builder().apply {
    connectTimeout(Constant.OkHttp_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(Constant.OkHttp_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(Constant.OkHttp_TIMEOUT, TimeUnit.SECONDS)
    addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
}

fun buildRetrofit(okHttpClient: OkHttpClient.Builder): Retrofit {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://newsapi.org")
        .client(okHttpClient.build())
        .build()
}