package miu.compro.cs743.myapplication.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import miu.compro.cs743.myapplication.base.BaseViewModel
import miu.compro.cs743.myapplication.model.enum.Status
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.repository.RemoteRepository
import miu.compro.cs743.myapplication.model.repository.RoomRepository

class SearchViewModel(private val defaultDispatcher: CoroutineDispatcher,
                      private val repositoryRemote: RemoteRepository,
                      private val repositoryRoom: RoomRepository
) : BaseViewModel() {

    private val _articles = MutableLiveData<List<Article>?>()
    val article : LiveData<List<Article>?> = _articles


    fun searchArticleByKeyword(keyword: String?) {
        viewModelScope.launch (defaultDispatcher) {
            keyword?.let {
                repositoryRemote.searchArticleByKeyword(it).let { baseApiResult ->
                    when (baseApiResult.status) {
                        Status.SUCCESS -> {
                            baseApiResult.data?.let { result ->
                                when (result.status) {
                                    "ok" -> {
                                        result.articles?.let { articles ->
                                            _articles.postValue(articles)
                                        }
                                    }
                                    else -> {
                                        _error.postValue(result.message)
                                    }
                                }
                            }
                        }

                        Status.ERROR -> {
                            _error.postValue(baseApiResult.message)
                        }
                    }
                }
            }
        }
    }

    fun insert(article: Article) {
        viewModelScope.launch(defaultDispatcher) {
            repositoryRoom.insertBookmark(article = article)
        }
    }
}