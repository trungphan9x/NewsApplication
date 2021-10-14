package miu.compro.cs743.myapplication.injection

import miu.compro.cs743.myapplication.model.remote.api.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(ApiService::class.java) }
}