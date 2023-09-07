package jp.android.app.features.login

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.android.app.common_ui.theme.*
import jp.android.app.common_ui.ui.*
import jp.android.app.common_ui.util.*
import jp.android.app.common_ui.util.Function
import jp.android.app.resources.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    windowWidth: WindowWidth,
    onLoginSuccess: Function,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorBackground),
    ) {
        ResImage(
            resId = R.drawable.bg_login,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.5f,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.padding16)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VMargin40()
            ResImage(
                resId = android.R.drawable.ic_dialog_map,
                colorFilter = ColorFilter.tint(colorText),
                modifier = Modifier
                    .size(Dimens.loginLogoSize),
            )
            VMargin40()
            Text(
                text = stringResource(R.string.welcome),
                style = TextStyle12,
                color = colorText
            )
            VMargin16()
            LoginContent(
                viewModel = viewModel,
                windowWidth = windowWidth,
                onSignUpConfirmed = {
                },
                onLoginCompleted = {
                    if (it) {
                        onLoginSuccess()
                    }
                },
            )
        }
    }
}

@Composable
private fun LoginContent(
    viewModel: LoginViewModel,
    windowWidth: WindowWidth,
    onSignUpConfirmed: Function,
    onLoginCompleted: (Boolean) -> Unit,
) {
    var isSignInScreen by rememberSaveable { mutableStateOf(true) }
    val alignmentBias by animateFloatAsState(if (isSignInScreen) -1f else 1f)
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max),
    ) {
        Row {
            val onTabSelected = {
                isSignInScreen = !isSignInScreen
            }
            HMargin8()
            LoginTab(
                title = stringResource(id = R.string.sign_in),
                isSelected = isSignInScreen,
                contentDescription = "Tab Sign In",
                onTabSelected = onTabSelected,
            )
            HMargin16()
            LoginTab(
                title = stringResource(id = R.string.sign_up),
                isSelected = !isSignInScreen,
                contentDescription = "Tab Sign Up",
                onTabSelected = onTabSelected,
            )
            HMargin8()
        }
        VMargin4()
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .padding(horizontal = Dimens.padding16)
                .height(2.dp)
                .align(BiasAlignment.Horizontal(alignmentBias))
                .background(
                    color = colorText,
                    shape = RoundedCornerShape(corner = CornerSize(1.dp)),
                )
        )
    }
    if (isSignInScreen) {
        ContentSignIn(
            viewModel = viewModel,
            windowWidth = windowWidth,
            onLoginCompleted = onLoginCompleted,
        )
    } else {
        ContentSignUp(
            onSignUpConfirmed = onSignUpConfirmed,
        )
    }
}

@Composable
private fun LoginTab(
    title: String,
    isSelected: Boolean,
    contentDescription: String,
    onTabSelected: Function,
) {
    Text(
        modifier = Modifier
            .contentDescription(contentDescription)
            .clickableNoRipple(
                enabled = !isSelected,
                onClick = onTabSelected,
            ),
        text = title,
        style = if (isSelected) {
            TextStyle16Bold.copy(color = colorText)
        } else {
            TextStyle16Bold.copy(color = colorText.copy(alpha = 0.3f))
        },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ContentSignIn(
    viewModel: LoginViewModel,
    windowWidth: WindowWidth,
    onLoginCompleted: (Boolean) -> Unit,
) {
    viewModel.event.collectEvent {
        when (it) {
            is LoginEvent.LoginSuccess -> {
                onLoginCompleted(true)
            }

            is LoginEvent.LoginFailure -> {
                onLoginCompleted(false)
            }
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState by viewModel.uiState.collectAsState()
    val isSignInEnable by remember {
        derivedStateOf {
            uiState.email.isNotBlank() && uiState.password.isNotBlank()
        }
    }

    LoadingDialog(uiState.isLoginLoading)

    ErrorDialog(uiState.appException, viewModel::onClearError)

    Column {
        VMargin40()
        Text(
            text = stringResource(R.string.login_title),
            color = colorText,
            style = TextStyle16
        )
        VMargin8()
        Text(
            text = stringResource(R.string.login_subtitle),
            color = colorText,
            style = TextStyle12,
        )
        VMargin16()
        if (windowWidth == WindowWidth.Large) {
            Row {
                SignInTextField(
                    modifier = Modifier
                        .weight(1f),
                    contentDescription = "TextField Sign In Email",
                    text = uiState.email,
                    leftIcon = R.drawable.ic_setting,
                    textHint = stringResource(id = R.string.email_hint),
                    keyboardType = KeyboardType.Email,
                ) {
                    viewModel.onEmailOrPasswordChanged(email = it)
                }
                HMargin16()
                SignInTextField(
                    modifier = Modifier
                        .weight(1f),
                    contentDescription = "TextField Sign In Password",
                    text = uiState.password,
                    leftIcon = R.drawable.ic_setting,
                    textHint = stringResource(id = R.string.password_hint),
                    isPassword = true,
                    keyboardType = KeyboardType.Password,
                ) {
                    viewModel.onEmailOrPasswordChanged(password = it)
                }
            }
        } else {
            SignInTextField(
                contentDescription = "TextField Sign In Email",
                text = uiState.email,
                leftIcon = R.drawable.ic_setting,
                textHint = stringResource(id = R.string.email_hint),
                keyboardType = KeyboardType.Email,
            ) {
                viewModel.onEmailOrPasswordChanged(email = it)
            }
            VMargin16()
            SignInTextField(
                contentDescription = "TextField Sign In Password",
                text = uiState.password,
                leftIcon = R.drawable.ic_setting,
                textHint = stringResource(id = R.string.password_hint),
                isPassword = true,
                keyboardType = KeyboardType.Password,
            ) {
                viewModel.onEmailOrPasswordChanged(password = it)
            }
        }
        VMargin4()
        RememberCheckBox(
            checked = uiState.isRemember,
            label = stringResource(R.string.remember),
            onCheckedChange = {
                viewModel.onRememberCheckedChanged(it)
            }
        )
        VMargin16()
        LoginButton(
            text = stringResource(R.string.sign_in),
            onClick = {
                keyboardController?.hide()
                viewModel.onLoginPressed()
            },
            enabled = isSignInEnable,
        )
        VMargin16()
    }
}

@Composable
private fun ContentSignUp(
    onSignUpConfirmed: Function,
) {
    Column {
        VMargin40()
        Text(
            text = stringResource(R.string.login_title),
            color = colorText,
            style = TextStyle16
        )
        VMargin8()
        Text(
            text = stringResource(R.string.login_subtitle),
            color = colorText,
            style = TextStyle12,
        )
        VMargin40()
        LoginButton(
            text = stringResource(R.string.sign_up),
            onClick = onSignUpConfirmed,
        )
    }
}

@Composable
fun SignInTextField(
    modifier: Modifier = Modifier,
    contentDescription: String,
    leftIcon: Int,
    textHint: String,
    text: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType,
    onTextChanged: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = leftIcon),
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )
            HMargin8()
            BasicTextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier
                    .contentDescription(contentDescription)
                    .weight(1f),
                textStyle = TextStyle12.copy(color = colorText),
                singleLine = true,
                cursorBrush = SolidColor(colorText),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = textHint,
                            color = colorText.copy(alpha = 0.3f),
                            style = TextStyle12,
                        )
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                ),
                visualTransformation = if (isPassword && !passwordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
            )
            HMargin8()
            if (isPassword) {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp),
                    onClick = {
                        passwordVisible = !passwordVisible
                    },
                ) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        tint = colorText.copy(alpha = 0.8f),
                        contentDescription = null,
                    )
                }
            } else if (text.isNotEmpty()) {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp),
                    onClick = {
                        onTextChanged("")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        tint = colorText.copy(alpha = 0.8f),
                        contentDescription = null,
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = colorText,
        )
    }
}

@Composable
fun LoginButton(
    text: String,
    onClick: Function,
    enabled: Boolean = true,
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        color = if (enabled) colorText else colorText.copy(alpha = 0.4f),
        style = TextStyle14Bold,
        modifier = Modifier
            .fillMaxWidth()
            .contentDescription("Button Login")
            .border(
                border = BorderStroke(
                    1.dp,
                    if (enabled) Color.White else Color.White.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(24.dp),
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick,
            )
            .background(if (enabled) Color(0xFFE5E1E0) else Color(0x66E5E1E0))
            .padding(PaddingValues(vertical = 10.dp))
    )
}

@Composable
fun RememberCheckBox(
    onCheckedChange: (Boolean) -> Unit,
    checked: Boolean,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickableNoRipple {
            onCheckedChange(!checked)
        }
    ) {
        Checkbox(
            checked = checked, onCheckedChange = null, colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF4B4B4B),
                checkmarkColor = Color(0xFFE5E1E0),
            ),
            modifier = Modifier
                .size(20.dp)
                .scale(0.6f)
        )
        HMargin4()
        Text(
            text = label,
            color = colorText,
            style = TextStyle10,
        )
    }
}