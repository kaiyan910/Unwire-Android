package hk.olleh.unwire

import android.app.Application
import hk.olleh.unwire.common.commonModule
import hk.olleh.unwire.post.postModule
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                commonModule
                    .toMutableList()
                    .apply {
                        add(postModule)
                    }
            )
        }
        JodaTimeAndroid.init(this)
    }
}