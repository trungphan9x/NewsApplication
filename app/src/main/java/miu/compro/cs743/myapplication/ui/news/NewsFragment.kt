package miu.compro.cs743.myapplication.ui.news

import android.os.Bundle
import android.view.View
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentNewsBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val newsViewModel: NewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraData()
        setObserve()
    }

    private fun getExtraData() {
        newsViewModel.topicTab = arguments?.getString(KEY_TOPIC) ?: ""
    }

    private fun setObserve() {
        newsViewModel.articles.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                setRecyclerView(articles)
            }
        })
    }

    private fun setRecyclerView(articles: List<Article>) {
        binding.rvArticles.adapter = NewsAdapter(articles)
    }

    companion object {
        const val ERROR_MESSAGE = 0
        private const val KEY_TOPIC = "topic"
        fun newInstance(topicName: String? = null) = NewsFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_TOPIC, topicName)
            }
        }
    }

}