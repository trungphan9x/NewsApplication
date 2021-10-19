package miu.compro.cs743.myapplication.ui.activity.articledetail

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebViewClient
import android.widget.MediaController
import com.bumptech.glide.Glide
import miu.compro.cs743.myapplication.base.BaseActivity
import miu.compro.cs743.myapplication.databinding.ActivityArticleDetailBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import org.koin.android.viewmodel.ext.android.viewModel

class ArticleDetailActivity : BaseActivity<ActivityArticleDetailBinding>(ActivityArticleDetailBinding::inflate) {

    private val articleDetailVM: ArticleDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showActionBar()
        getExtraData()
        setUpView()
    }

    private fun showActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = null
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun getExtraData() {
        if(intent.hasExtra("article")) {
            articleDetailVM.article = intent.getSerializableExtra("article") as? Article
        }

        if(intent.hasExtra("isVideo")) {
            articleDetailVM.isVideo = intent.getBooleanExtra("isVideo", false)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setUpView() {
        when (articleDetailVM.isVideo) {
            true -> {
                binding.webView.visibility = GONE
                binding.videoHolder.visibility = VISIBLE

                articleDetailVM.article?.let{
                    binding.videoView.apply {
                        setVideoPath(it.url)
                        val mediaController = MediaController(this.context)
                        mediaController.setAnchorView(this)
//                        mediaController.show(0)
                        setMediaController(mediaController)
                        start()
                    }

                    binding.tvTitle.text = it.title
                    binding.tvSource.text = it.source.name
                    binding.tvPublishedDate.text = it.publishedAtModified
                    binding.tvContent.text = it.description
                }
            }
            false -> {
                binding.webView.visibility = VISIBLE
                binding.videoHolder.visibility = GONE
                binding.webView.apply {
                    articleDetailVM.article?.url?.let { url ->
                        webViewClient = WebViewClient()
                        loadUrl(url)
                        settings.javaScriptEnabled = true
                    }
                }
            }

        }
    }
}