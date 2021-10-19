package miu.compro.cs743.myapplication.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentProfileBinding
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment
import miu.compro.cs743.myapplication.ui.fragments.newslist.PhotoNewsAdapter
import miu.compro.cs743.myapplication.util.NewsAppSharedPreference
import miu.compro.cs743.myapplication.util.getCurrentUser
import miu.compro.cs743.myapplication.util.removeAllData
import miu.compro.cs743.myapplication.util.setCurrentUser
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModel()
    private val sharedPreference: NewsAppSharedPreference by inject()
    private val args: ProfileFragmentArgs by navArgs<ProfileFragmentArgs>()
    private lateinit var photoAdapter: PhotoNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraDataFromLogin()
        setListener()
        setObserver()
        setRecyclerView()
        viewModel.getAllBookmark()
    }



    private fun getExtraDataFromLogin() {
        if(applicationContext().getCurrentUser() == null) {
            args.user?.let {
                applicationContext().setCurrentUser(it)
            }
        }
        setView()
    }

    private fun setObserver() {
        viewModel.article.observe(viewLifecycleOwner, {
            it?.let {
                if(!it.isNullOrEmpty()) {
                    binding.llProfileHolder.visibility = GONE
                    binding.rvArticles.visibility = VISIBLE
                    photoAdapter.setItems(it)
                } else {
                    binding.llProfileHolder.visibility = VISIBLE
                    binding.rvArticles.visibility = GONE
                }
            }
        })
    }

    private fun setListener() {
        binding.ivSetting.setOnClickListener {
            PopupMenu(requireActivity(), it).apply {
                menuInflater.inflate(R.menu.setting_popup_menu, this.menu)
                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.logout -> {
                            applicationContext().removeAllData()
                            getNav(binding.root).navigate(R.id.action_navigation_profile_self)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.delete_all_bookmark -> {
                            viewModel.deleteAllBookmark()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                show()
            }
        }
    }

    private fun setView() {
        when (applicationContext().getCurrentUser()) {
            null -> {
                if (getNav(binding.root).currentDestination?.id == R.id.navigation_profile) {
                    getNav(binding.root).navigate(R.id.action_navigation_profile_to_loginFragment)
                }
            }
            else -> {
                val user = applicationContext().getCurrentUser()
                user?.let {
                    binding.tvEmail.text = it.email
                    "${it.firstname} ${it.lastname}".also {
                        binding.tvFullName.text = it
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvArticles.setHasFixedSize(true)
        binding.rvArticles.layoutManager = LinearLayoutManager(context)

        photoAdapter = PhotoNewsAdapter().apply {
            setOnItemClickListener { which, position, article, rootView ->
                when (which) {
                    NewsListFragment.ITEM_CLICKED -> {
                        val intent = Intent(
                            requireActivity(),
                            ArticleDetailActivity::class.java
                        ).apply {
                            putExtra("article", article)
                            putExtra("isVideo", false)
                        }
                        startActivity(intent)

                    }
                    NewsListFragment.UNBOOKMARK_CLICKED -> {
                        viewModel.deleteBookmark(article)
                    }
                    NewsListFragment.SHARE_CLICKED -> {
                        article.url?.let {
                            share(article.url)
                        }
                    }
                }
            }
        }
        binding.rvArticles.adapter = photoAdapter
    }

}