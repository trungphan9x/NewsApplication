package miu.compro.cs743.myapplication.ui.activity.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseActivity
import miu.compro.cs743.myapplication.databinding.ActivityMainBinding
import miu.compro.cs743.myapplication.model.enum.CurrentTab
import miu.compro.cs743.myapplication.util.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }
    private val mainViewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        setObserver()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_video,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun setListener() {

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.navigation_home -> {
                    if(applicationContext.getCurrentTab() != CurrentTab.HOME.name) applicationContext.setCurrentTab(CurrentTab.HOME.name)
                    supportActionBar?.isShowing?.let { isShow->
                        if (isShow) supportActionBar?.hide()
                    }
                    binding.container.hideKeyboard()

                }
                R.id.navigation_search -> {
                    if(applicationContext.getCurrentTab() != CurrentTab.SEARCH.name) applicationContext.setCurrentTab(CurrentTab.SEARCH.name)
                    supportActionBar?.isShowing?.let { isShow->
                        if (!isShow) supportActionBar?.show()
                    }
                }
                R.id.navigation_video -> {
                    if(applicationContext.getCurrentTab() != CurrentTab.VIDEO.name) applicationContext.setCurrentTab(CurrentTab.VIDEO.name)
                    supportActionBar?.isShowing?.let { isShow->
                        if (isShow) supportActionBar?.hide()
                    }
                    binding.container.hideKeyboard()
                }
                R.id.navigation_profile -> {
                    if(applicationContext.getCurrentTab() != CurrentTab.PROFILE.name) applicationContext.setCurrentTab(CurrentTab.PROFILE.name)
                    supportActionBar?.isShowing?.let { isShow->
                        if (isShow) supportActionBar?.hide()
                    }
                    binding.container.hideKeyboard()
                }
            }
        }
    }

    private fun setObserver() {
        mainViewModel.selectedLanguage.observe(this, {
            it?.let {
                changeLanguage(it)
            }
        })
    }
}