package miu.compro.cs743.myapplication.ui.fragments.home

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()

    //private val tabs = arrayListOf<String>("general", "business", "entertainment", "health", "science", "sports", "technology")
    private var tabsMap = hashMapOf<String, String>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeTabName()
        setViewPager()
    }

    private fun initializeTabName() {
        tabsMap = hashMapOf<String, String>(
            "general" to getString(R.string.tab_genral),
            "business" to getString(R.string.tab_business),
            "entertainment" to getString(R.string.tab_entertainment),
            "health" to getString(R.string.tab_health),
            "science" to getString(R.string.tab_science),
            "sports" to getString(R.string.tab_sports),
            "technology" to getString(R.string.tab_technology)
        )
    }

    private fun setViewPager() {
        val adapter = HomePagerAdapter(childFragmentManager, lifecycle, tabsMap.keys.toTypedArray())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsMap.values.toTypedArray()[position]
        }.attach()
    }


}