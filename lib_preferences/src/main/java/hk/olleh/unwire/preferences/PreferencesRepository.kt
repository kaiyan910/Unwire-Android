package hk.olleh.unwire.preferences

interface PreferencesRepository {

    fun setDarkMode(darkMode: Boolean)
    fun isDarkMode(): Boolean
}