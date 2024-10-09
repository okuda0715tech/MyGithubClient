package com.kurodai0715.mygithubclient.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurodai0715.mygithubclient.data.ProfileRepository
import com.kurodai0715.mygithubclient.data.source.local.GithubPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LoginUiState(
    val pat: String = "",
    val patVisible: Boolean = false,
    val retainPat: Boolean = false,
    val responseCode: Int? = null,
)

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val githubPrefRepo: GithubPreferencesRepository,
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
        Log.v(TAG, "loadData is stated.")
        viewModelScope.launch {
            val githubPreferences = githubPrefRepo.githubPreferences.first()
            Log.d(TAG, "GithubPreferences = $githubPreferences")
            _uiState.update {
                it.copy(
                    pat = githubPreferences.pat,
                    patVisible = githubPreferences.patVisibility,
                    retainPat = githubPreferences.retainPat
                )
            }
        }

        Log.v(TAG, "loadData is ended.")
    }

    private fun updateResponseCode(code: Int) {
        Log.d(TAG, "response code = $code")
        _uiState.update {
            it.copy(
                responseCode = code
            )
        }
    }

    /**
     * API 実行結果のレスポンスコードをクリア.
     */
    fun clearResponseCode() {
        _uiState.update {
            Log.v(TAG, "response code is cleared.")
            it.copy(responseCode = null)
        }
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
            githubPrefRepo.updatePat(pat)
        }
    }

    /**
     * PAT の表示/非表示状態を更新する.
     *
     * プリファレンスを更新する。画面の状態は自動的には更新されないため、別途更新が必要です。
     */
    fun savePatVisibilityToPref(checked: Boolean) {
        viewModelScope.launch {
            githubPrefRepo.updatePatVisibility(checked)
        }
    }

    /**
     * PAT のマスクの有無の状態を更新する.
     *
     * プリファレンスは更新せず、画面の状態のみを更新する。
     */
    fun updatePatVisibility(checked: Boolean) {
        Log.d(TAG, "checked = $checked")
        _uiState.update {
            it.copy(patVisible = checked)
        }
    }

    /**
     * PAT をアプリに保存するかどうかのチェックボックスの状態を更新する.
     *
     * プリファレンスは更新せず、画面の状態のみを更新する。
     */
    fun updateRetainPat(checked: Boolean) {
        Log.d(TAG, "checked = $checked")
        _uiState.update {
            it.copy(retainPat = checked)
        }
    }

    /**
     * PAT をアプリに保存するかどうかのチェックボックスの状態を更新する.
     *
     * プリファレンスを更新する。画面の状態は自動的には更新されないため、別途更新が必要です。
     */
    fun saveRetainPatToPref(checked: Boolean) {
        viewModelScope.launch {
            githubPrefRepo.updateRetainPat(checked)
        }
    }

    /**
     * Github の user API をリクエストして、その結果を処理する.
     *
     * サーバーからデータをロードして、ローカルに保存する。
     * サーバーアクセスの結果を UI 状態に反映する。
     */
    fun loadProfile(pat: String = "") {
        viewModelScope.launch {
            val code = profileRepository.loadProfile(pat)
            updateResponseCode(code)
        }
    }

}