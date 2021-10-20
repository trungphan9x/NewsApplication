package miu.compro.cs743.myapplication.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import miu.compro.cs743.myapplication.R
import miu.compro.cs743.myapplication.base.BaseViewModel
import miu.compro.cs743.myapplication.model.data.Language
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.repository.RoomRepository

class ProfileViewModel(private val defaultDispatcher: CoroutineDispatcher,
                       private val repository: RoomRepository
) : BaseViewModel() {

    private val _articles = MutableLiveData<List<Article>?>()
    val article : LiveData<List<Article>?> = _articles

    fun getAllBookmark() {
        viewModelScope.launch(defaultDispatcher) {
            repository.getAllBookmark().let {
                _articles.postValue(it.sortedByDescending { it.bookmarkedAt })
            }
        }
    }

    fun deleteBookmark(article: Article) {
        viewModelScope.launch (defaultDispatcher) {
            article.title?.let {
                repository.deleteBookmark(article.title)?.let {
                    getAllBookmark()
                }
            }
        }
    }

    fun deleteAllBookmark() {
        viewModelScope.launch(defaultDispatcher) {
            repository.deleteAllBookmark()?.let {
                getAllBookmark()
            }
        }
    }

    fun loadLanguage(): ArrayList<Language> {
        return arrayListOf(
            Language(R.drawable.us, "English", "en"),
            Language(R.drawable.`in`, "Tamil", "ta"),
            Language(R.drawable.vn, "Vietnamese", "vi")
        )
    }
}