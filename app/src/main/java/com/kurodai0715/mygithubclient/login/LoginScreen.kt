package com.kurodai0715.mygithubclient.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kurodai0715.mygithubclient.R
import com.kurodai0715.mygithubclient.data.source.network.HttpResponse
import com.kurodai0715.mygithubclient.util.CircularLoading
import com.kurodai0715.mygithubclient.util.DebouncedButton

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    goToNextScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val onLogin = { pat: String, patVisible: Boolean, retainPat: Boolean ->
            if (retainPat) {
                viewModel.savePatToPref(pat)
            } else {
                viewModel.savePatToPref("")
                // Preference の状態が元々空文字だった場合は、状態が更新されないため、状態が通知されません。
                // そのため、ユーザーが入力した PAT を uiState から明示的にクリアする必要があります。
                viewModel.updatePat("")
            }
            viewModel.savePatVisibilityToPref(patVisible)
            viewModel.saveRetainPatToPref(retainPat)
            viewModel.loadProfile(pat)
        }

        LoginContent(
            pat = uiState.pat,
            onPatChanged = viewModel::updatePat,
            patVisible = uiState.patVisible,
            onClickPasswordVisibility = viewModel::updatePatVisibility,
            retainPat = uiState.retainPat,
            onRetainPatChanged = viewModel::updateRetainPat,
            onLogin = onLogin,
            loading = uiState.loading,
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        )

        uiState.responseCode?.let { code ->
            Log.d(TAG, "let block is started with responseCode = $code")

            val message = stringResource(id = HttpResponse.getMessageResIdBy(code))

            LaunchedEffect(code) {
                Log.d(TAG, "response code = $code")
                when (code) {
                    HttpResponse.OK.code, HttpResponse.NOT_MODIFIED.code -> {
                        Log.v(TAG, "success")
                        goToNextScreen()
                    }

                    else -> {
                        Log.v(TAG, "error")
                        snackbarHostState.showSnackbar(message = message)
                    }
                }
                viewModel.clearResponseCode()
            }
        }
    }
}

@Composable
fun LoginContent(
    pat: String,
    onPatChanged: (String) -> Unit,
    patVisible: Boolean,
    onClickPasswordVisibility: (Boolean) -> Unit,
    retainPat: Boolean,
    onRetainPatChanged: (Boolean) -> Unit,
    onLogin: (String, Boolean, Boolean) -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    // TODO パーソナルアクセストークンの取得方法の説明を軽く追加する。
    //  例えば、公式サイトの URL リンクだけでも OK。
    //  ただし、パーソナルアクセストークンには、旧型のクラッシックタイプと新型のきめ細かいタイプがあるため、
    //  どちらが必要なのか、あるいは両方必要なのかが確定してから情報を載せる。

    Box(modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = stringResource(id = R.string.login_prompt),
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                value = pat,
                onValueChange = onPatChanged,
                label = { Text(text = "Personal Access Token") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (patVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    if (patVisible) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_visibility_on_24),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable { onClickPasswordVisibility(false) }
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable { onClickPasswordVisibility(true) }
                        )
                    }
                }
            )

            Row(
                Modifier.toggleable(
                    value = retainPat,
                    role = Role.Checkbox,
                    onValueChange = onRetainPatChanged
                )
            ) {
                Checkbox(
                    checked = retainPat,
                    onCheckedChange = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )

                Text(
                    text = stringResource(id = R.string.save_token_checkbox_label),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            DebouncedButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    onLogin(pat, patVisible, retainPat)
                }
            ) {
                Text(text = stringResource(id = R.string.login_button_label))
            }
        }
        if (loading) {
            CircularLoading()
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(goToNextScreen = { })
}