package com.kick.npl.ui.app

import androidx.lifecycle.ViewModel
import com.kick.npl.data.local.AuthLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localDataSource: AuthLocalDataSource,
) : ViewModel() {
    val isSignedIn: Boolean
        get() = localDataSource.getAuthToken()?.isNotBlank() == true
}