package com.kick.npl.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val keyToken = "authToken"
    private val keyRefreshToken = "refreshToken"
    private val keyExpiredIn = "expiredIn"

    fun getAuthToken(): String? = sharedPreferences.getString(keyToken, null)

    fun saveAuthTokenInfo(tokenInfo: TokenInfo) {
        with (sharedPreferences.edit()) {
            putString(keyToken, tokenInfo.accessToken)
            putString(keyRefreshToken, tokenInfo.refreshToken)
            putString(keyExpiredIn, tokenInfo.expiresIn)
            apply()
        }
    }
}