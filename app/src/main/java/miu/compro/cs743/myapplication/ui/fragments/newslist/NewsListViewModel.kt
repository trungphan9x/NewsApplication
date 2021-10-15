package miu.compro.cs743.myapplication.ui.fragments.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import miu.compro.cs743.myapplication.base.BaseViewModel
import miu.compro.cs743.myapplication.model.enum.Status
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.repository.RemoteRepository

class NewsListViewModel(private val defaultDispatcher: CoroutineDispatcher,
                        private val repository: RemoteRepository
                    ) : BaseViewModel() {

    init {
        getArticlesByCategoryFromApi()
    }

    private val _articles = MutableLiveData<List<Article>?>()
    val articles: LiveData<List<Article>?> = _articles

    var topicTab: String? = null

    private fun getArticlesByCategoryFromApi() {
        viewModelScope.launch (defaultDispatcher) {
            topicTab?.let {
                repository.getArticleByCategory(it).let { baseApiResult ->
                    when (baseApiResult.status) {
                        Status.SUCCESS -> {
                            baseApiResult.data?.let { result ->
                                when (result.status) {
                                    "ok" -> {
                                        _articles.postValue(result.articles)
                                    }
                                    else -> {
                                        _articles.postValue(null)
                                    }
                                }
                            }
                        }

                        Status.ERROR -> {
                            _articles.postValue(null)
                        }
                    }
                }
            }
        }
    }

}