package miu.compro.cs743.myapplication.model.remote.api

import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.remote.response.NewsAppResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("https://newsapi.org/v2/top-headlines?country=us&apiKey=c393966132514b3990cba7dfa47e648b")
    suspend fun getArticleList(@Query("category") category: String): NewsAppResponseBody<List<Article>>
}