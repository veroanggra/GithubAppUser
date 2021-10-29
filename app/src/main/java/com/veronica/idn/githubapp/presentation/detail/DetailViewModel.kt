package com.veronica.idn.githubapp.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veronica.idn.githubapp.data.model.DetailUser
import com.veronica.idn.githubapp.data.repository.FavoriteRepository
import com.veronica.idn.githubapp.data.repository.UserRepository
import com.veronica.idn.githubapp.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _detailUser: MutableLiveData<DetailUser?> = MutableLiveData()
    val detailUser get() = _detailUser

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading get() = _loading

    private val _error: MutableLiveData<Throwable?> = MutableLiveData()
    val error get() = _error

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite get() = _isFavorite

    private val _listFavoriteUsers: MutableLiveData<List<DetailUser>?> = MutableLiveData()
    private var strUserName: String = ""

    init {
        getFavoriteUsers()
        _isFavorite.postValue(false)
    }

    fun isFavoriteItem(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                delete(favoriteUser)
            } else {
                insert(favoriteUser)
            }
        }
    }

    private fun insert(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            if (favoriteUser != null) {
                favoriteRepository.insert(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(true)

            }
        }
    }

    private fun delete(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            if (favoriteUser != null) {
                favoriteRepository.delete(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(false)
            }
        }
    }

    private fun getFavoriteUsers() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteUser().collect {
                _listFavoriteUsers.postValue(it)
            }
        }
    }

    fun showFavoriteUser(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            for (it in _listFavoriteUsers.value ?: mutableListOf()) {
                if (favoriteUser?.login == it.login) {
                    _isFavorite.postValue(true)
                    break
                } else {
                    _isFavorite.postValue(false)
                }
            }
        }
    }

    fun getDetailUser(username: String) {
        if (strUserName != username) {
            viewModelScope.launch {
                strUserName = username
                userRepository.getDetailUser(username).onStart {
                    _loading.value = true
                }.onCompletion {
                    _loading.value = false
                }.collect {
                    when (it) {
                        is ApiResult.Success -> {
                            _error.postValue(null)
                            _detailUser.postValue(it.data)
                        }
                        is ApiResult.Error -> {
                            _error.postValue(it.throwable)
                        }
                    }
                }
            }
        }
    }
}