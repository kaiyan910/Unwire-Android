package hk.olleh.unwire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import hk.olleh.unwire.common.miscellaneous.EventObserver
import hk.olleh.unwire.databinding.ActivityMainBinding
import hk.olleh.unwire.preferences.PreferencesRepository
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val preferencesRepository: PreferencesRepository by inject()

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (preferencesRepository.isDarkMode()) {
            setTheme(R.style.AppTheme_LightStatus_Dark)
        } else {
            setTheme(R.style.AppTheme_LightStatus)
        }

        DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {

                lifecycleOwner = this@MainActivity
                vm = viewModel
            }

        observe()
    }

    private fun observe() {

        viewModel
            .navigation
            .observe(this, EventObserver {

                when(it) {

                    is MenuPage.News -> {

                        findNavController(R.id.nav_host_fragment)
                            .navigate(R.id.postSelectionFragment)
                    }

                    is MenuPage.Mode -> {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                        finish()
                    }

                    else -> {}
                }
            })
    }
}
