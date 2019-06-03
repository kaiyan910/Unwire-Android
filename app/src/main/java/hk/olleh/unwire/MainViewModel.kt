package hk.olleh.unwire

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import hk.olleh.unwire.common.base.BaseViewModel
import hk.olleh.unwire.common.miscellaneous.Event
import hk.olleh.unwire.preferences.PreferencesRepository
import android.view.MenuItem

sealed class MenuPage {

    object News: MenuPage()
    object Search: MenuPage()
    object Bookmark: MenuPage()

    data class Mode(val darkMode: Boolean): MenuPage()
}

class MainViewModel(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    val navigation: MutableLiveData<Event<MenuPage>> = MutableLiveData()

    private var currentPage: MenuPage? = null

    fun onBottomMenuClicked(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_news -> {

                if (currentPage != MenuPage.News) {
                    currentPage = MenuPage.News
                    navigation.value = Event(MenuPage.News)
                }
                return true
            }
            R.id.menu_search -> {
                if (currentPage != MenuPage.Search) {
                    currentPage = MenuPage.Search
                    navigation.value = Event(MenuPage.Search)
                }
                return true
            }
            R.id.menu_bookmark -> {
                if (currentPage != MenuPage.Bookmark) {
                    currentPage = MenuPage.Bookmark
                    navigation.value = Event(MenuPage.Bookmark)
                }
                return true
            }
            R.id.menu_mode -> {
                val darMode = !preferencesRepository.isDarkMode()
                preferencesRepository.setDarkMode(darMode)
                navigation.value = Event(MenuPage.Mode(darMode))
                return true
            }
        }

        return false
    }
}