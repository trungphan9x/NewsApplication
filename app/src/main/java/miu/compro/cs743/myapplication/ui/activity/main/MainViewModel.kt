package miu.compro.cs743.myapplication.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import miu.compro.cs743.myapplication.base.BaseViewModel

class MainViewModel(private val defaultDispatcher: CoroutineDispatcher) : BaseViewModel() {

    val _selectedLanguage: MutableLiveData<String?> = MutableLiveData()
    val selectedLanguage: LiveData<String?> get() = _selectedLanguage

    fun setSelectedLanguage(language : String?) {
        viewModelScope.launch (defaultDispatcher){
            _selectedLanguage.postValue(language)
        }
    }
}