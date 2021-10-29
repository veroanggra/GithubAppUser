package com.veronica.idn.githubapp.presentation.detail.following

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veronica.idn.githubapp.data.model.User
import com.veronica.idn.githubapp.data.repository.UserRepository
import com.veronica.idn.githubapp.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private var strUsername: String = ""
    private val _followingLiveData = MutableLiveData<List<User?>?>()
    val followingLiveData get() = _followingLiveData

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading get() = _loading

    private val _error: MutableLiveData<Throwable?> = MutableLiveData()
    val error get() = _error

    fun getFollowing(username: String) {
        if (strUsername != username) {
            viewModelScope.launch {
                strUsername = username
                userRepository.getFollowing(username).onStart {
                    _loading.value = true
                }.onCompletion {
                    _loading.value = false
                }.collect {
                    when (it) {
                        is ApiResult.Success -> {
                            _error.postValue(null)
                            _followingLiveData.postValue(it.data)
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