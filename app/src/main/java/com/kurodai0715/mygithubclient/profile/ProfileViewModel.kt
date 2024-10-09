package com.kurodai0715.mygithubclient.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurodai0715.mygithubclient.R
import com.kurodai0715.mygithubclient.data.ProfileRepository
import com.kurodai0715.mygithubclient.data.Profile
import com.kurodai0715.mygithubclient.util.Async
import com.kurodai0715.mygithubclient.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProfileUiState(
    val profile: Profile? = null
)

private const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    /**
     * 更新用
     */
    private val _uiState = MutableStateFlow(ProfileUiState())

    /**
     * 読み取り専用
     */
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        profileRepository.fetchProfileStream().onEach { localProfile ->
            _uiState.update {
                it.copy(profile = localProfile)
            }
        }.launchIn(viewModelScope)
    }

}