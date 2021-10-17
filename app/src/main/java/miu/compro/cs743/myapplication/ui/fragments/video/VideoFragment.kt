package miu.compro.cs743.myapplication.ui.fragments.video

import android.content.Intent
import android.os.Bundle
import android.view.View
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentVideoBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED
import org.koin.android.viewmodel.ext.android.viewModel

class VideoFragment : BaseFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate) {
    private val videoViewModel: VideoViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserve()
    }

    private fun setObserve() {
        videoViewModel.articles.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                setRecyclerView(articles)
            }
        })
    }

    private fun setRecyclerView(articles: List<Article>) {
        binding.rvArticles.adapter = VideoNewsAdapter(articles).apply {
            setOnItemClickListener { which, position, article, rootView ->
                when (which) {
                    ITEM_CLICKED -> {
                        val intent = Intent(requireActivity(), ArticleDetailActivity::class.java).apply {
                            putExtra("article", article)
                            putExtra("isVideo", true)
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
}