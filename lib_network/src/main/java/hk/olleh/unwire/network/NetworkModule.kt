package hk.olleh.unwire.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single<Interceptor>(named("http")) {

        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
    }

    single {

        OkHttpClient()
            .newBuilder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(get(named("http")))
                }
            }
            .build()
    }

    single<Converter.Factory> { MoshiConverterFactory.create() }

    single {

        Retrofit
            .Builder()
            .baseUrl("https://unwire.hk")
            .addConverterFactory(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(UnwireApi::class.java) }

    single<RemoteDataSource> { RetrofitRemoteDataSource(get()) }
}
