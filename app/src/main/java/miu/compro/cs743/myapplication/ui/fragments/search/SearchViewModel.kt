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
                      private val repositoryRoom: RoomRepository
) : BaseViewModel() {

    fun insert(article: Article) {
        viewModelScope.launch(defaultDispatcher) {
            repositoryRoom.insertBookmark(article = article)
        }
    }
}