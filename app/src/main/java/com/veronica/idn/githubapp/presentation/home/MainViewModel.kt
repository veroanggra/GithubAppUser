package com.veronica.idn.githubapp.presentation.home

import androidx.lifecycle.*
import androidx.room.Index
import com.veronica.idn.githubapp.data.model.User
import com.veronica.idn.githubapp.data.repository.ThemeRepository
import com.veronica.idn.githubapp.data.repository.UserRepository
import com.veronica.idn.githubapp.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersRepository: UserRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {
    private val _listUserLivedData = MutableLiveData<List<User?>?>()
    val listUserLiveData get() = _listUserLivedData


    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading get() = _loading

    private val _error: MutableLiveData<Throwable?> = MutableLiveData()
    val error get() = _error

    init {
        getUser()
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return themeRepository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(mode: Boolean) {
        viewModelScope.launch {
            themeRepository.saveThemeSetting(mode)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            usersRepository.getUser().onStart {
                _loading.value = true
            }.onCompletion {
                _loading.value = false
            }.collect {
                when (it) {
                    is ApiResult.Success -> {
                        _error.postValue(null)
                        _listUserLivedData.postValue(it.data)
                    }
                    is ApiResult.Error -> {
                        _error.postValue(it.throwable)
                    }
                }
            }
        }
    }

    fun getSearchUser(username: String) {
        if (username == "") {
            getUser()
        } else {
            viewModelScope.launch {
                usersRepository.getListSearchUser(username).onStart {
                    _loading.value = true
                }.onCompletion {
                    _loading.value = false
                }.collect {
                    when (it) {
                        is ApiResult.Success -> {
                            _error.postValue(null)
                            _listUserLivedData.postValue(it.data)
                        }
                        is ApiResult.Error -> _error.postValue(it.throwable)
                    }
                }
            }
        }
    }

}