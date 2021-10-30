package miu.compro.cs743.myapplication.ui.fragments.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentSearchBinding
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.PhotoNewsAdapter
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED
import org.koin.android.viewmodel.ext.android.viewModel
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_ARTICLE
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_IS_VIDEO
import miu.compro.cs743.myapplication.ui.activity.main.MainViewModel
import miu.compro.cs743.myapplication.util.getCurrentUser
import org.koin.android.viewmodel.ext.android.sharedViewModel


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var photoAdapter: PhotoNewsAdapter
    private val mainViewModel : MainViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeData()
    }

    private fun observeData() {
        mainViewModel.article.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                binding.tvErrorNotification.visibility = GONE
                photoAdapter.setItems(it)
            }
        })

        searchViewModel.error.observe(viewLifecycleOwner, {
            binding.tvErrorNotification.visibility = VISIBLE
            binding.tvErrorNotification.text = it
        })


    }

    private fun setRecyclerView() {
        binding.rvArticles.setHasFixedSize(true)
        binding.rvArticles.layoutManager = LinearLayoutManager(context)

        photoAdapter = PhotoNewsAdapter().apply {
            setOnItemClickListener { which, _, article, _ ->
                when (which) {
                    ITEM_CLICKED -> {
                        val intent = Intent(
                            requireActivity(),
                            ArticleDetailActivity::class.java
                        ).apply {
                            putExtra(DETAIL_ARTICLE, article)
                            putExtra(DETAIL_IS_VIDEO, false)
                        }
                        startActivity(intent)

                    }
                    BOOKMARK_CLICKED -> {
                        if(applicationContext().getCurrentUser() != null) {
                            searchViewModel.insert(article)
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.news_list_noti_login_to_save_bookmark), Toast.LENGTH_SHORT).show()
                        }
                    }
                    SHARE_CLICKED -> {
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