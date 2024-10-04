package com.kurodai0715.mygithubclient.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurodai0715.mygithubclient.data.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LoginUiState(
    val pat: String = "",
    val retainPat: Boolean = false
)

const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    /**
     * 更新用
     */
    private val _loginUiState = MutableStateFlow(LoginUiState())

    /**
     * 読み取り専用
     */
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    init {
        loadData()
    }

    /**
     * 画面表示用データをデータレイヤーから取得する.
     */
    private fun loadData() {
        profileRepository.getPatStream()
            .onEach { savedPat ->
                _loginUiState.update {
                    it.copy(pat = savedPat)
                }
            }
            .launchIn(viewModelScope)

        profileRepository.getRetainPat()
            .onEach { checked ->
                _loginUiState.update {
                    it.copy(retainPat = checked)
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * 画面表示用の PAT を更新する.
     */
    fun updatePat(newPat: String) {
        _loginUiState.update {
            it.copy(pat = newPat)
        }
    }

    /**
     * PAT を PreferenceDataStore に保存する.
     */
    fun savePatToPref(pat: String) {
        viewModelScope.launch {
            profileRepository.updatePat(pat)
        }
    }

    fun updateRetainPat(checked: Boolean) {
        Log.d("test", "checked = $checked")
        _loginUiState.update {
            it.copy(retainPat = checked)
        }
    }

    fun saveRetainPatToPref(checked: Boolean) {
        viewModelScope.launch {
            profileRepository.updateRetainPat(checked)
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            profileRepository.loadProfile()
        }
    }

}