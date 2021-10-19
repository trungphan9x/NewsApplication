package miu.compro.cs743.myapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import miu.compro.cs743.myapplication.model.remote.response.Article
import miu.compro.cs743.myapplication.util.UIEvent

open class BaseViewModel :ViewModel() {
    val _uiEvent: MutableLiveData<UIEvent<Int>> = MutableLiveData()
    val uiEvent: LiveData<UIEvent<Int>> get() = _uiEvent
//
//    val _error = MutableLiveData<String?>()
//    val error: LiveData<String?> = _error

    val _error: MutableLiveData<String?> = MutableLiveData()
    val error: LiveData<String?> get() = _error
}