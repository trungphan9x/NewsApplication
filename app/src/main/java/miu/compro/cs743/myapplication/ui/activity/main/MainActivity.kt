package miu.compro.cs743.myapplication.ui.activity.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseActivity
import miu.compro.cs743.myapplication.databinding.ActivityMainBinding
import miu.compro.cs743.myapplication.ui.fragments.home.HomeFragment
import miu.compro.cs743.myapplication.ui.fragments.profile.ProfileFragment
import miu.compro.cs743.myapplication.ui.fragments.search.SearchFragment
import miu.compro.cs743.myapplication.ui.fragments.video.VideoFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }

    private val mainViewModel by viewModel<MainViewModel> {
        parametersOf(navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
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
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {

                    return@setOnItemSelectedListener true
                }
                R.id.navigation_search -> {

                    return@setOnItemSelectedListener true
                }
                R.id.navigation_video -> {

                    return@setOnItemSelectedListener true
                }
                R.id.navigation_profile -> {

                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }

        binding.navView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {

                }
                R.id.navigation_search -> {

                }
                R.id.navigation_video -> {

                }
                R.id.navigation_profile -> {

                }
            }
        }
    }
}