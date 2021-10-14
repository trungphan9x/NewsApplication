package miu.compro.cs743.myapplication.injection

import miu.compro.cs743.myapplication.model.repository.RemoteRepository
import miu.compro.cs743.myapplication.model.repository.RemoteRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<RemoteRepository> { RemoteRepositoryImpl(get()) }
}