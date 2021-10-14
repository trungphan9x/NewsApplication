package miu.compro.cs743.myapplication.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import miu.compro.cs743.myapplication.base.BaseViewModel

class VideoViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is video Fragment"
    }
    val text: LiveData<String> = _text
}