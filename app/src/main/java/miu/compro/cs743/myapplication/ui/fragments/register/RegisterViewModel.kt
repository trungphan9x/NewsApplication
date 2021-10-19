package miu.compro.cs743.myapplication.ui.fragments.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import miu.compro.cs743.myapplication.base.BaseViewModel
import miu.compro.cs743.myapplication.model.repository.RoomRepository
import miu.compro.cs743.myapplication.model.roomdb.entity.User

class RegisterViewModel(private val defaultDispatcher: CoroutineDispatcher,
                        private val repository: RoomRepository
) : BaseViewModel() {

    private val _resultInsertUser = MutableLiveData<List<Long>?>()
    val resultInsertUser: LiveData<List<Long>?> = _resultInsertUser

    fun insertUserToRoom(user: User) {
        viewModelScope.launch (defaultDispatcher) {
            _resultInsertUser.postValue(repository.insertUser(user))
        }
    }


}