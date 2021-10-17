package miu.compro.cs743.myapplication.ui.activity.articledetail

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebViewClient
import android.widget.MediaController
import miu.compro.cs743.myapplication.base.BaseActivity
import miu.compro.cs743.myapplication.databinding.ActivityArticleDetailBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import org.koin.android.viewmodel.ext.android.viewModel

class ArticleDetailActivity : BaseActivity<ActivityArticleDetailBinding>(ActivityArticleDetailBinding::inflate) {

    private val articleDetailVM: ArticleDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtraData()
        setUpView()
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

//                        val mediaController: MediaController = object : MediaController(this.context) {
//                            override fun hide() {
//                                //Do not hide.
//                            }
//                        }
                        val mediaController = MediaController(this.context)
                        mediaController.setAnchorView(this)
//                        mediaController.show(0)
                        setMediaController(mediaController)
                    }

                    binding.tvTitle.text = it.title
                    binding.tvSource.text = it.source.name
                    binding.tvPublishedDate.text = it.publishedAt
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