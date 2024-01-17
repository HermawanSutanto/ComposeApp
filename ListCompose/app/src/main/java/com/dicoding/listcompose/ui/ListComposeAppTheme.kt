package com.dicoding.listcompose.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable

@Composable
fun ListComposeAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = typography,
        shapes = shapes,
        content = content
    )
}
