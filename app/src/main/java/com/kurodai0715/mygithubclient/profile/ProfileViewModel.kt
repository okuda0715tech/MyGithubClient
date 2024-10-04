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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ProfileUiState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

const val TAG = "ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    // TODO ログイン画面とプロフィール画面で ViewModel を分けられるので、分ける。
    //  これより下がプロフィール画面の処理なので、このあたりをわける。

    /**
     * 更新用
     */
    private val _uiState = MutableStateFlow(ProfileUiState())

    /**
     * 読み取り専用
     */
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()



    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _profileAsync = profileRepository.fetchProfileStream()
        .map { handleProfile(it) }
        .catch { emit(Async.Error(R.string.loading_profile_error)) }

    val profileUiState: StateFlow<ProfileUiState> = combine(
        _userMessage, _isLoading, _profileAsync
    ) { userMessage, isLoading, profileAsync ->
        when (profileAsync) {
            Async.Loading -> {
                Log.i(TAG, "Async.Loading")
                ProfileUiState(isLoading = true)
            }

            is Async.Error -> {
                Log.i(TAG, "Async.Error")
                ProfileUiState(userMessage = userMessage)
            }

            is Async.Success -> {
                Log.i(TAG, "Async.Success")
                ProfileUiState(
                    profile = profileAsync.data,
                    isLoading = isLoading
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = ProfileUiState(isLoading = true)
        )

    fun snackbarMessageShown() {
        _userMessage.value = null
    }

    private fun handleProfile(profile: Profile?): Async<Profile?> {
        if (profile == null) {
            return Async.Error(R.string.profile_not_found)
        }
        Log.v(TAG, "Async.Success with $profile")
        return Async.Success(profile)
    }
}