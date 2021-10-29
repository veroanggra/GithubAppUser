package com.veronica.idn.githubapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veronica.idn.githubapp.data.model.DetailUser
import com.veronica.idn.githubapp.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) :
    ViewModel() {
    private val _favoriteUserLiveData: MutableLiveData<List<DetailUser
            >?> = MutableLiveData()
    val favoriteUserLiveData get() = _favoriteUserLiveData

    private val _error: MutableLiveData<Throwable?> = MutableLiveData()
    val error get() = _error

    init {
        getFavoriteUser()
    }

    fun getFavoriteUser() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteUser().collect {
                _favoriteUserLiveData.postValue(it)
            }
        }
    }

}