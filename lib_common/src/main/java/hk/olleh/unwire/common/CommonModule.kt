package hk.olleh.unwire.common

import hk.olleh.unwire.common.repository.CacheableRepository
import hk.olleh.unwire.common.repository.Repository
import hk.olleh.unwire.network.networkModule
import org.koin.dsl.module

val repositoryModule = module {

    single<Repository> { CacheableRepository(get()) }
}

val commonModule = listOf(networkModule, repositoryModule)