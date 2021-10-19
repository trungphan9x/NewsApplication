package miu.compro.cs743.myapplication.model.repository

import androidx.lifecycle.LiveData
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.roomdb.dao.BookmarkDao
import miu.compro.cs743.myapplication.model.roomdb.dao.UserDao
import miu.compro.cs743.myapplication.model.roomdb.entity.BookmarkEntity
import miu.compro.cs743.myapplication.model.roomdb.entity.User

class RoomRepositoryImpl(
    private val userDao: UserDao,
    private val bookmarkDao: BookmarkDao
) : RoomRepository {

    /**
     * USER
     */
    override suspend fun getAllUsers() = userDao.getAll()

    override suspend fun insertUser(user: User) = userDao.insertAll(user)

    override suspend fun findByEmail(email: String) = userDao.findByEmail(email)


    /**
     * BOOKMARK
     */
    override suspend fun getAllBookmark(): List<Article> {
        val articles = ArrayList<Article>()
        bookmarkDao.getAllBookmark().let { bookmarks ->
            bookmarks.forEach { bookmark ->
                articles.add(
                    Article(
                        author = bookmark.author,
                        content = bookmark.content,
                        description = bookmark.description,
                        publishedAt = bookmark.publishedAt,
                        source = bookmark.source,
                        title = bookmark.title,
                        url = bookmark.url,
                        urlToImage = bookmark.urlToImage,
                        bookmarkedAt = bookmark.bookmarkedAt
                    )
                )
            }
        }
        return articles
    }

    override suspend fun insertBookmark(article: Article) : List<Long> {
        return bookmarkDao.insertAllBookmark(BookmarkEntity(
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            source = article.source,
            title = article.title ?: "",
            url = article.url,
            urlToImage = article.urlToImage
        ))
    }

    override suspend fun deleteAllBookmark() : Int? {
        return bookmarkDao.deleteAllBookmark()
    }

    override suspend fun deleteBookmark(titleBookmark: String): Int? {
        return bookmarkDao.deleteBookmark(titleBookmark)
    }

//    override suspend fun deleteBookmark(article: Article): Int? {
//        return bookmarkDao.deleteBookmark(BookmarkEntity(
//            author = article.author,
//            content = article.content,
//            description = article.description,
//            publishedAt = article.publishedAt,
//            source = article.source,
//            title = article.title,
//            url = article.url,
//            urlToImage = article.urlToImage
//        ))
//    }
}