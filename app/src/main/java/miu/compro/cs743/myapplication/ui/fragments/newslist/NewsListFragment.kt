package miu.compro.cs743.myapplication.ui.fragments.newslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentNewsBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
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
            setOnItemClickListener { which, position, article, rootView ->
                when (which) {
                    ITEM_CLICKED -> {
                        val intent = Intent(requireActivity(), ArticleDetailActivity::class.java).apply {
                            putExtra("article", article)
                            putExtra("isVideo", false)
                        }
                        startActivity(intent)

                    }
                    BOOKMARK_CLICKED -> {

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

        const val KEY_TOPIC = "topic"

        fun newInstance(topicName: String? = null) = NewsListFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_TOPIC, topicName)
            }
        }
    }

}