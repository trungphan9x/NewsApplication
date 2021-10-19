package miu.compro.cs743.myapplication.model.remote.response

import java.io.Serializable

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val isBookmark: Boolean = false,
    var bookmarkedAt: Long? = null,
) : Serializable {
    val publishedAtModified: String?
        get() = publishedAt?.replace("T", " ")?.removeRange(16,20)
}