package miu.compro.cs743.myapplication.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import miu.compro.cs743.myapplication.util.UIEvent

open class BaseViewModel :ViewModel() {
    val _uiEvent: MutableLiveData<UIEvent<Int>> = MutableLiveData()
    val uiEvent: LiveData<UIEvent<Int>> get() = _uiEvent
}