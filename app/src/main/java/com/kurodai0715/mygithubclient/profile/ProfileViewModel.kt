package com.kurodai0715.mygithubclient.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurodai0715.mygithubclient.data.ProfileRepository
import com.kurodai0715.mygithubclient.data.Profile
import com.kurodai0715.mygithubclient.data.UserRepo
import com.kurodai0715.mygithubclient.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val profile: Profile? = null,
    val userRepos: List<UserRepo>? = null,
)

private const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            // user/repos API を実行して、結果をローカルに保存する。
            profileRepository.loadUserRepos()
        }
    }

    private val _user = profileRepository.fetchProfileStream()
    private val _userRepos = profileRepository.fetchUserReposStream()

    val uiState: StateFlow<ProfileUiState> = combine(
        _user, _userRepos
    ) { user, userRepos ->
        ProfileUiState(profile = user, userRepos = userRepos)
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ProfileUiState()
        )

}