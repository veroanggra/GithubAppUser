package com.veronica.idn.githubapp.presentation.detail.follower

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
class FollowersViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private var strUserName: String = ""

    private val _followersLiveData = MutableLiveData<List<User?>?>()
    val followerLiveData get() = _followersLiveData

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading get() = _loading

    private val _error: MutableLiveData<Throwable?> = MutableLiveData()
    val error get() = _error

    fun getFollowers(username: String) {
        if (strUserName != username) {
            viewModelScope.launch {
                strUserName = username
                userRepository.getFollowers(username).onStart {
                    _loading.value = true
                }.onCompletion {
                    _loading.value = false
                }.collect {
                    when (it) {
                        is ApiResult.Success -> {
                            _error.postValue(null)
                            followerLiveData.postValue(it.data)
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