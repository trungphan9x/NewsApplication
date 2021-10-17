package miu.compro.cs743.myapplication.model.repository

import miu.compro.cs743.myapplication.base.BaseApiResult
import miu.compro.cs743.myapplication.base.BaseRepository
import miu.compro.cs743.myapplication.model.remote.api.ApiService
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.remote.response.NewsAppResponseBody

class RemoteRepositoryImpl(private val apiService: ApiService) : BaseRepository(), RemoteRepository {
    override suspend fun getArticleByCategory(category: String) : BaseApiResult<NewsAppResponseBody<List<Article>>> {
        return safeApi {
            apiService.getArticleList(category)
        }
    }

    override suspend fun searchArticleByKeyword(keyword: String): BaseApiResult<NewsAppResponseBody<List<Article>>> {
        return safeApi {
            apiService.searchArticleByKeyword(keyword)
        }
    }

    override suspend fun getVideoArticles(): BaseApiResult<NewsAppResponseBody<List<Article>>> {
        return safeApi {
            apiService.getVideoArticles()
        }
    }


}