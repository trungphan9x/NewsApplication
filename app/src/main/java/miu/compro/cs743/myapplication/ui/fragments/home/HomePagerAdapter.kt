package miu.compro.cs743.myapplication.ui.fragments.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import miu.compro.cs743.myapplication.ui.fragments.listnews.NewsFragment

class HomePagerAdapter(fm: FragmentManager, lc: Lifecycle, tabs: ArrayList<String>) : FragmentStateAdapter(fm, lc) {
    private val tabList = tabs
    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return NewsFragment.newInstance(tabList[position])
    }

}