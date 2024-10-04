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
import com.kurodai0715.mygithubclient.profile.ProfileViewModel

@Composable
fun LoginScreen(
    goToNextScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    val onLogin = { retainPat: Boolean, pat: String ->
        if (retainPat) {
            viewModel.savePatToPref(pat)
        }
        viewModel.saveRetainPatToPref(retainPat)
        viewModel.loadProfile()
        // TODO データのロードに失敗した場合は、画面遷移せずに、エラーメッセージを画面に表示する。
        goToNextScreen()
    }

    Log.d("test", "uiState.retainPat = ${uiState.retainPat}")

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
    
    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = stringResource(id = R.string.login_prompt), modifier = Modifier.padding(8.dp))
        TextField(
            value = pat,
            onValueChange = onPatChanged,
            label = { Text(text = "hint") },
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