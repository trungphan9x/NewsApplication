package miu.compro.cs743.myapplication.ui.fragments.video

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentVideoBinding
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED
import org.koin.android.viewmodel.ext.android.viewModel

class VideoFragment : BaseFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate) {
    private val videoViewModel: VideoViewModel by viewModel()
    private lateinit var videoAdapter: VideoNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setObserve()
    }

    private fun setObserve() {
        videoViewModel.articles.observe(viewLifecycleOwner, { articles ->
            articles?.let {
                binding.tvErrorNotification.visibility = View.GONE
                videoAdapter.setItems(it)
            }
        })

        videoViewModel.error.observe(viewLifecycleOwner, {
            binding.tvErrorNotification.visibility = View.VISIBLE
            binding.tvErrorNotification.text = it
        })
    }

    private fun setRecyclerView() {
        binding.rvArticles.setHasFixedSize(true)
        binding.rvArticles.layoutManager = LinearLayoutManager(context)
        videoAdapter = VideoNewsAdapter().apply {
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
                        article.url?.let {
                            share(article.url)
                        }
                    }
                }
            }
        }
        binding.rvArticles.adapter = videoAdapter
    }
}