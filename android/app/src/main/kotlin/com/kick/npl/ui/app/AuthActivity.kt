package com.kick.npl.ui.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.R
import com.kick.npl.ui.theme.NPLTheme
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.UiState
import com.kick.npl.ui.util.noRippleClickable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        this.onBackPressedDispatcher.addCallback(this, backPressedCallback)
        super.onCreate(savedInstanceState)
        setContent {
            NPLTheme(preventSystemUiModification = true) {
                val context = LocalContext.current
                val result by viewModel.result.collectAsStateWithLifecycle()

                LaunchedEffect(result) {
                    when (val resulted = result) {
                        is UiState.Error -> {
                            Toast.makeText(context, resulted.message, Toast.LENGTH_SHORT).show()
                        }
                        is UiState.Success -> {
                            setResult(RESULT_OK)
                            finish()
                        }
                        else -> {}
                    }
                }

                AuthScreen(
                    loginWithKakaoAccount = { viewModel.loginWithKakaoAccount(context) },
                    loginWithKakaoTalk = { viewModel.loginWithKakaoTalk(context) },
                )
            }
        }
    }
}

@Composable
private fun AuthScreen(
    loginWithKakaoTalk: () -> Unit = {},
    loginWithKakaoAccount: () -> Unit = {},
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "주차장\n정상화",
                style = Theme.typo.title1,
                fontSize = 50.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Theme.colors.onBackground0,
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape
                    )
                    .background(Theme.colors.surface)
                    .padding(6.dp)
                    .border(
                        width = 6.dp,
                        color = Theme.colors.onBackground0,
                        shape = CircleShape
                    )
                    .padding(36.dp)
            )

            Spacer(modifier = Modifier.height(80.dp))

            Row(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = true
                    )
                    .background(
                        color = Color(0xFFFEE500),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(16.dp)
                    .noRippleClickable(loginWithKakaoTalk),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_24_share_talk),
                    contentDescription = "Kakao icon"
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "카카오 로그인",
                    style = Theme.typo.bodyB,
                    color = Color(0xFF191D23)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                Modifier
                    .width(IntrinsicSize.Max)
                    .noRippleClickable(loginWithKakaoAccount),
            ) {
                Text(
                    text = "카카오계정 직접 입력",
                    style = Theme.typo.subhead,
                    color = Theme.colors.onBackground0
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Theme.colors.onBackground0
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAuthScreen() {
    NPLTheme {
        AuthScreen()
    }
}