package miu.compro.cs743.myapplication.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import miu.compro.cs743.myapplication.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Profile Fragment"
    }
    val text: LiveData<String> = _text
}