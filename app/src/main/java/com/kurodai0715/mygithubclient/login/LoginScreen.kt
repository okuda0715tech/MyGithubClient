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
import com.kurodai0715.mygithubclient.R

@Composable
fun LoginScreen(onLogin: () -> Unit) {

    var isSaveTokenChecked by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.padding(12.dp)) {
        Text(text = stringResource(id = R.string.login_prompt), modifier = Modifier.padding(8.dp))
        TextField(
            value = "",
            onValueChange = {},
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
                onCheckedChange = null,
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
            onClick = onLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.login_button_label))
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(onLogin = { })
}