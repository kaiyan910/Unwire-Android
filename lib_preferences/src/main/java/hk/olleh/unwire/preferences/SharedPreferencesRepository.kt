package hk.olleh.unwire.preferences

import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesRepository(
    private val sharedPreferences: SharedPreferences
) : PreferencesRepository {

    override fun setDarkMode(darkMode: Boolean) = sharedPreferences
        .edit { putBoolean(PrefsKey.DARK_MODE, darkMode) }

    override fun isDarkMode(): Boolean =
        sharedPreferences.getBoolean(PrefsKey.DARK_MODE, false)
}