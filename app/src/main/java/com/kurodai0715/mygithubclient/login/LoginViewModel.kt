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
    private val _uiState = MutableStateFlow(LoginUiState())

    /**
     * 読み取り専用
     */
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    /**
     * 画面表示用データをデータレイヤーから取得する.
     */
    private fun loadData() {
        profileRepository.getPatStream()
            .onEach { savedPat ->
                _uiState.update {
                    it.copy(pat = savedPat)
                }
            }
            .launchIn(viewModelScope)

        profileRepository.getRetainPat()
            .onEach { checked ->
                _uiState.update {
                    it.copy(retainPat = checked)
                }
            }
            .launchIn(viewModelScope)
    }

    /**
     * 画面表示用の PAT を更新する.
     */
    fun updatePat(newPat: String) {
        _uiState.update {
            it.copy(pat = newPat)
        }
    }

    /**
     * PAT をローカルに保存する.
     */
    fun savePatToPref(pat: String) {
        viewModelScope.launch {
            profileRepository.updatePat(pat)
        }
    }

    /**
     * PAT をアプリに保存するかどうかのチェックボックスの状態を更新する.
     *
     * プリファレンスは更新せず、画面の状態のみを更新する。
     */
    fun updateRetainPat(checked: Boolean) {
        Log.d("test", "checked = $checked")
        _uiState.update {
            it.copy(retainPat = checked)
        }
    }

    /**
     * PAT をアプリに保存するかどうかのチェックボックスの状態を更新する.
     *
     * プリファレンスを更新する。それに連動して、画面の状態も自動的に更新される。
     */
    fun saveRetainPatToPref(checked: Boolean) {
        viewModelScope.launch {
            profileRepository.updateRetainPat(checked)
        }
    }

    /**
     * サーバーからデータをロードして、ローカルに保存する。
     */
    fun loadProfile() {
        viewModelScope.launch {
            profileRepository.loadProfile()
        }
    }

}