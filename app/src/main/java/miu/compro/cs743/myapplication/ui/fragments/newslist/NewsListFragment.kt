package miu.compro.cs743.myapplication.ui.fragments.newslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentNewsBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class NewsListFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val newsListViewModel: NewsListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraData()
        setObserve()
    }

    private fun getExtraData() {
        newsListViewModel.topicTab = arguments?.getString(KEY_TOPIC) ?: ""
    }

    private fun setObserve() {
        newsListViewModel.articles.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                setRecyclerView(articles)
            }
        })
    }

    private fun setRecyclerView(articles: List<Article>) {
        binding.rvArticles.adapter = NewsAdapter(articles).apply {
            setOnItemClickListener { which, position, article, rootView ->
                when (which) {
                    ITEM_CLICKED -> {
                        val intent = Intent(requireActivity(), ArticleDetailActivity::class.java).apply {
                            putExtra("article", article)
                        }
                        startActivity(intent)

                    }
                    BOOKMARK_CLICKED -> {

                    }
                    SHARE_CLICKED -> {

                    }
                }
            }
        }
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