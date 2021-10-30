package miu.compro.cs743.myapplication.ui.activity.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseActivity
import miu.compro.cs743.myapplication.databinding.ActivityMainBinding
import miu.compro.cs743.myapplication.model.enum.CurrentTab
import miu.compro.cs743.myapplication.util.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    SearchView.OnQueryTextListener {

    private val navController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }
    private val mainViewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListener()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home,
//                R.id.navigation_search,
//                R.id.navigation_video,
//                R.id.navigation_profile
//            )
//        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }


    private fun initSearView() {
        binding.searchView.apply {
            isIconifiedByDefault = true
            isFocusable = true
            isIconified = false
            requestFocusFromTouch()
            setOnQueryTextListener(this@MainActivity)
        }
    }

    private fun setListener() {

        mainViewModel.showKeyboard.observe(this, {
            binding.root.showKeyboard()
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    applicationContext.setCurrentTab(CurrentTab.HOME.name)
                    binding.appBar.visibility = GONE
                    binding.container.hideKeyboard()

                }
                R.id.navigation_search -> {
                    applicationContext.setCurrentTab(CurrentTab.SEARCH.name)
                    binding.appBar.visibility = VISIBLE
                    initSearView()
                }
                R.id.navigation_video -> {
                    applicationContext.setCurrentTab(CurrentTab.VIDEO.name)
                    binding.appBar.visibility = GONE
                    binding.container.hideKeyboard()
                }
                R.id.navigation_profile -> {
                    applicationContext.setCurrentTab(CurrentTab.PROFILE.name)
                    binding.appBar.visibility = GONE
                    binding.container.hideKeyboard()
                }
            }
        }
    }

    override fun onQueryTextSubmit(keyword: String?): Boolean {
        mainViewModel.searchArticleByKeyword(keyword)
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }
}