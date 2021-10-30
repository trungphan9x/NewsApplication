package miu.compro.cs743.myapplication.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import miu.compro.cs743.myapplication.base.BaseViewModel
import miu.compro.cs743.myapplication.model.enum.Status
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.model.repository.RemoteRepository

class MainViewModel(private val defaultDispatcher: CoroutineDispatcher,
                    private val repositoryRemote: RemoteRepository) : BaseViewModel() {

    val _showKeyboard: MutableLiveData<String?> = MutableLiveData()
    val showKeyboard: LiveData<String?> get() = _showKeyboard

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
}