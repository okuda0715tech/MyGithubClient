package com.kurodai0715.mygithubclient.util

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import com.kurodai0715.mygithubclient.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CircularLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitEachGesture {
                    // タッチダウンイベントを消費して、イベントチェーンを打ち切る。
                    awaitFirstDown().also {
                        it.consume()
                    }
                }
            }
            // TODO テーマを利用して、固定色を直接参照するのをやめる。
            .background(colorResource(id = R.color.translucent_gray_55))
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

/**
 * 連打抑止ボタン.
 *
 * @param debounceTimeMillis 連続でタップが可能になるまでの時間（ミリ秒）
 */
@Composable
fun DebouncedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    debounceTimeMillis: Long = 300,
    content: @Composable () -> Unit
) {
    var isClickable by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            if (isClickable) {
                onClick()
                isClickable = false
                coroutineScope.launch {
                    // タップ間隔の制限
                    delay(debounceTimeMillis)
                    isClickable = true
                }
            }
        },
        modifier = modifier
    ) {
        content()
    }
}