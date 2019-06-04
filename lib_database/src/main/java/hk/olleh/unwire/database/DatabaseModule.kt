package hk.olleh.unwire.database

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {

        Room
            .databaseBuilder(
                androidApplication(),
                AppDatabase::class.java,
                "unwire-db"
            )
            .build()
    }

    single<DatabaseDataSource> { RoomDataSource(get()) }
}