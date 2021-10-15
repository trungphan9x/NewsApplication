package miu.compro.cs743.myapplication.ui.fragments.home

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by viewModel()

    private val tabs = arrayListOf<String>("general", "business", "entertainment", "health", "science", "sports", "technology")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
    }

    private fun setViewPager() {
        val adapter = HomePagerAdapter(childFragmentManager, lifecycle, tabs)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }


}