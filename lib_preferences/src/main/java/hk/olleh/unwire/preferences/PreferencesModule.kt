package hk.olleh.unwire.preferences

import android.content.SharedPreferences
import android.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferecnesModule = module {

    single<SharedPreferences> {
        PreferenceManager.getDefaultSharedPreferences(androidContext())
    }

    single<PreferencesRepository> {
        SharedPreferencesRepository(get())
    }
}