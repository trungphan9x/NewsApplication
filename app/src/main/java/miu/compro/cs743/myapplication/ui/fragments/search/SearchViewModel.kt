package miu.compro.cs743.myapplication.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import miu.compro.cs743.myapplication.base.BaseViewModel

class SearchViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment"
    }
    val text: LiveData<String> = _text
}