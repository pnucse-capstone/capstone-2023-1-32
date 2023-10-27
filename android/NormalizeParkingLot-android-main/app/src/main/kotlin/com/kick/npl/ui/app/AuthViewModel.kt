package com.kick.npl.ui.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.data.local.AuthLocalDataSource
import com.kick.npl.data.local.TokenInfo
import com.kick.npl.ui.util.UiState
import com.kick.npl.ui.util.getIdleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val localDataSource: AuthLocalDataSource,
) : ViewModel() {

    private val _result = MutableStateFlow(getIdleUiState<Unit>())
    val result = _result.asStateFlow()

    fun loginWithKakaoTalk(context: Context) {
        if (isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if ((error is ClientError && error.reason == ClientErrorCause.Cancelled).not()) {
                        error.printStackTrace()
                        onSignInFailure()
                    }
                } else if (token != null) {
                    onSignInSuccess(token)
                }
            }
        } else {
            loginWithKakaoAccount(context)
        }
    }

    fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                error.printStackTrace()
                onSignInFailure()
            } else if (token == null) {
                onSignInFailure("사용자 토큰이 존재하지 않습니다")
            } else {
                onSignInSuccess(token)
            }
        }
    }

    private fun isKakaoTalkLoginAvailable(context: Context): Boolean {
        return UserApiClient.instance.isKakaoTalkLoginAvailable(context)
    }

    private fun onSignInSuccess(token: OAuthToken) = viewModelScope.launch(Dispatchers.IO) {
        _result.update { UiState.Success(Unit) }
        localDataSource.saveAuthTokenInfo(token.toTokenInfo())
    }

    private fun onSignInFailure(message: String? = null) = viewModelScope.launch(Dispatchers.IO) {
        _result.update { UiState.Error(message ?: "로그인에 실패하였습니다") }
    }

    private fun OAuthToken.toTokenInfo(): TokenInfo {
        return TokenInfo(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}
