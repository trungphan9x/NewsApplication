package miu.compro.cs743.myapplication.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import miu.compro.cs743.myapplication.base.BaseViewModel
import miu.compro.cs743.myapplication.model.repository.RoomRepository
import miu.compro.cs743.myapplication.model.roomdb.entity.User

class LoginViewModel(private val defaultDispatcher: CoroutineDispatcher,
                     private val repository: RoomRepository
) : BaseViewModel() {

    init {
        getUser()
    }

    var user: User? = null

    private val _users : MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>> = _users

    private fun getUser() {
        viewModelScope.launch (defaultDispatcher){
            _users.postValue(repository.getAllUsers())
        }
    }

}