package miu.compro.cs743.myapplication.model.repository

import miu.compro.cs743.myapplication.base.BaseApiResult
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.remote.response.NewsAppResponseBody

interface RemoteRepository {
    suspend fun getArticleByCategory(category: String) : BaseApiResult<NewsAppResponseBody<List<Article>>>

    suspend fun searchArticleByKeyword(keyword: String) : BaseApiResult<NewsAppResponseBody<List<Article>>>

    suspend fun getVideoArticles() : BaseApiResult<NewsAppResponseBody<List<Article>>>
}