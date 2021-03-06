package miu.compro.cs743.myapplication.ui.fragments.newslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentNewsBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_ARTICLE
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity.Companion.DETAIL_IS_VIDEO
import miu.compro.cs743.myapplication.util.NewsAppSharedPreference
import miu.compro.cs743.myapplication.util.getCurrentUser
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class NewsListFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val newsListViewModel: NewsListViewModel by viewModel()
    private lateinit var photoAdapter: PhotoNewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        getExtraData()
        setObserve()
        newsListViewModel.getArticlesByCategoryFromApi()
    }

    private fun getExtraData() {
        newsListViewModel.topicTab = arguments?.getString(KEY_TOPIC) ?: ""
    }

    private fun setObserve() {
        newsListViewModel.articles.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                binding.tvErrorNotification.visibility = View.GONE
                photoAdapter.setItems(articles)
            }
        })

        newsListViewModel.error.observe(viewLifecycleOwner, {
            binding.tvErrorNotification.visibility = View.VISIBLE
            binding.tvErrorNotification.text = it
        })
    }

    private fun setRecyclerView() {
        binding.rvArticles.setHasFixedSize(true)
        binding.rvArticles.layoutManager = LinearLayoutManager(context)
        photoAdapter  = PhotoNewsAdapter().apply {
            setOnItemClickListener { which, _, article, _ ->
                when (which) {
                    ITEM_CLICKED -> {
                        val intent = Intent(requireActivity(), ArticleDetailActivity::class.java).apply {
                            putExtra(DETAIL_ARTICLE, article)
                            putExtra(DETAIL_IS_VIDEO, false)
                        }
                        startActivity(intent)

                    }
                    BOOKMARK_CLICKED -> {
                       if(applicationContext().getCurrentUser() != null) {
                           newsListViewModel.insert(article)
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

    companion object {

        const val ERROR_MESSAGE = 0
        const val BOOKMARK_CLICKED = 1
        const val SHARE_CLICKED = 2
        const val ITEM_CLICKED = 3
        const val UNBOOKMARK_CLICKED = 4

        const val KEY_TOPIC = "topic"

        fun newInstance(topicName: String? = null) = NewsListFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_TOPIC, topicName)
            }
        }
    }

}