package com.kurodai0715.mygithubclient.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kurodai0715.mygithubclient.R


@Composable
fun LoginScreen(
    goToNextScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val onLogin = { retainPat: Boolean, pat: String ->
        if (retainPat) {
            viewModel.savePatToPref(pat)
        } else {
            viewModel.savePatToPref("")
            // Preference の状態が元々空文字だった場合は、状態が更新されないため、状態が通知されません。
            // そのため、ユーザーが入力した PAT を uiState から明示的にクリアする必要があります。
            viewModel.updatePat("")
        }
        viewModel.saveRetainPatToPref(retainPat)
        viewModel.loadProfile(pat)
        goToNextScreen()
    }

    LoginContent(
        pat = uiState.pat,
        onPatChanged = viewModel::updatePat,
        retainPat = uiState.retainPat,
        onRetainPatChanged = viewModel::updateRetainPat,
        onLogin = onLogin
    )
}

@Composable
fun LoginContent(
    pat: String,
    onPatChanged: (String) -> Unit,
    retainPat: Boolean,
    onRetainPatChanged: (Boolean) -> Unit,
    onLogin: (Boolean, String) -> Unit,
) {
    // TODO パーソナルアクセストークンの取得方法の説明を軽く追加する。
    //  例えば、公式サイトの URL リンクだけでも OK。

    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = stringResource(id = R.string.login_prompt), modifier = Modifier.padding(8.dp))
        // TODO この入力エリアをマスクしたり解除できる機能を追加する。
        TextField(
            value = pat,
            onValueChange = onPatChanged,
            label = { Text(text = "Personal Access Token") },
            modifier = Modifier.fillMaxWidth()
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
        Button(
            onClick = {
                onLogin(retainPat, pat)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.login_button_label))
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(goToNextScreen = { })
}