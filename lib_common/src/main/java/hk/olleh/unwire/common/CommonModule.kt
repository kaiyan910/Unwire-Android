package hk.olleh.unwire.common

import hk.olleh.unwire.common.repository.DatabaseRepository
import hk.olleh.unwire.common.repository.RetrofitRemoteRepository
import hk.olleh.unwire.common.repository.RemoteRepository
import hk.olleh.unwire.common.repository.RoomDatabaseRepository
import hk.olleh.unwire.database.databaseModule
import hk.olleh.unwire.network.networkModule
import hk.olleh.unwire.preferences.preferecnesModule
import org.koin.dsl.module

val repositoryModule = module {

    single<RemoteRepository> { RetrofitRemoteRepository(get()) }

    single<DatabaseRepository> { RoomDatabaseRepository(get()) }
}

val commonModule = listOf(networkModule, databaseModule, repositoryModule, preferecnesModule)