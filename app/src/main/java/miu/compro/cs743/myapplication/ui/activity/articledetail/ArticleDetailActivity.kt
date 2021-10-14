package miu.compro.cs743.myapplication.ui.activity.articledetail

import android.os.Bundle
import android.webkit.WebViewClient
import miu.compro.cs743.myapplication.base.BaseActivity
import miu.compro.cs743.myapplication.databinding.ActivityArticleDetailBinding
import miu.compro.cs743.myapplication.model.remote.response.Article
import org.koin.android.viewmodel.ext.android.viewModel

class ArticleDetailActivity : BaseActivity<ActivityArticleDetailBinding>(ActivityArticleDetailBinding::inflate) {

    private val articleDetailVM: ArticleDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtraData()
        setUpWebView()
    }

    private fun getExtraData() {
        if(intent.hasExtra("article")) {
            articleDetailVM.article = intent.getSerializableExtra("article") as? Article
        }
    }

    private fun setUpWebView() {
        binding.webView.apply {
            articleDetailVM.article?.url?.let { url ->
                webViewClient = WebViewClient()
                loadUrl(url)
                settings.javaScriptEnabled = true
            }
        }
    }
}