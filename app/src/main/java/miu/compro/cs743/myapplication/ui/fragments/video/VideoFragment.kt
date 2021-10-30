package miu.compro.cs743.myapplication.ui.fragments.video

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import miu.compro.cs743.myapplication.NewsApplication.Companion.applicationContext
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseFragment
import miu.compro.cs743.myapplication.databinding.FragmentVideoBinding
import miu.compro.cs743.myapplication.ui.activity.articledetail.ArticleDetailActivity
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.BOOKMARK_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.ITEM_CLICKED
import miu.compro.cs743.myapplication.ui.fragments.newslist.NewsListFragment.Companion.SHARE_CLICKED
import miu.compro.cs743.myapplication.util.getCurrentUser
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
            setOnItemClickListener { which, _, article, rootView ->
                when (which) {
                    ITEM_CLICKED -> {
                        ArticleDetailActivity.startActivity(requireActivity(), rootView, article, isVideo = true)
                    }
                    BOOKMARK_CLICKED -> {
                        if(applicationContext().getCurrentUser() != null) {
                            videoViewModel.insert(article)
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
        binding.rvArticles.adapter = videoAdapter
    }
}