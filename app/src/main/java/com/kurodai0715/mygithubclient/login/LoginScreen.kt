package com.kurodai0715.mygithubclient.login

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState2.collectAsStateWithLifecycle()

    val onClick = { isSavingTokenChecked: Boolean, pat: String ->
        if (isSavingTokenChecked) {
            viewModel.savePatToPref(pat)
        }
        viewModel.getProfile(isSavingTokenChecked)
        goToNextScreen()
    }

    LoginContent(
        pat = uiState.pat,
        onPatChanged = viewModel::updatePat,
        onLogin = onClick
    )
}

@Composable
fun LoginContent(
    pat: String,
    onPatChanged: (String) -> Unit,
    onLogin: (Boolean, String) -> Unit,
) {

    var isSaveTokenChecked by remember {
        mutableStateOf(false)
    }

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
                value = isSaveTokenChecked,
                role = Role.Checkbox,
                onValueChange = { isSaveTokenChecked = !isSaveTokenChecked })
        ) {
            Checkbox(
                checked = isSaveTokenChecked,
                onCheckedChange = { isSaveTokenChecked = it },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = stringResource(id = R.string.save_token_checkbox_label),
                modifier = Modifier.padding(8.dp)
            )
        }
        Button(
            onClick = {
                onLogin(isSaveTokenChecked, pat)
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