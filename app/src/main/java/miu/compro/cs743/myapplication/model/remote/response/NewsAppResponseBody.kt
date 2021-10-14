package miu.compro.cs743.myapplication.model.remote.response

data class NewsAppResponseBody<T> (
    val status: String,
    val totalResults: Int,
    val articles: T
)