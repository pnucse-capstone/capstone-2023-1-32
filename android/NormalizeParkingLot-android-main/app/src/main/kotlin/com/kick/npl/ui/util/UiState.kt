package com.kick.npl.ui.util

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}

fun <T> getIdleUiState() : UiState<T> = UiState.Idle