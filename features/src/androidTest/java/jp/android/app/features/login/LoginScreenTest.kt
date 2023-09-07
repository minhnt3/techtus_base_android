package jp.android.app.features.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import jp.android.app.common_ui.theme.colorText
import jp.android.app.common_ui.util.WindowWidth
import jp.android.app.domain.use_case.LoginUseCase
import jp.android.app.features.login.LoginScreen
import jp.android.app.features.login.LoginViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import jp.android.app.resources.R
import jp.android.app.test_util.EMAIL
import jp.android.app.test_util.PASSWORD
import jp.android.app.test_util.assertTextColor
import jp.android.app.test_util.stringRes
import kotlin.test.assertEquals

class LoginScreenTest {

    @get:Rule
    var rule = MockKRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var loginUseCase: LoginUseCase

    private lateinit var viewModel: LoginViewModel

    @Before
    fun init() {
        viewModel = LoginViewModel(loginUseCase)
        composeTestRule.setContent {
            LoginScreen(viewModel, WindowWidth.Normal) {
            }
        }
        composeTestRule.onRoot().printToLog("all_nodes")
    }

    @Test
    fun switchTab() {
        composeTestRule.onNodeWithContentDescription("Tab Sign Up").performClick()
        composeTestRule.onNodeWithContentDescription("Tab Sign Up").assertTextColor(colorText)
        composeTestRule.onNodeWithContentDescription("Tab Sign In").assertTextColor(colorText.copy(alpha = 0.3f))
        composeTestRule.onNodeWithContentDescription("TextField Sign In Email").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("TextField Sign In Password").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Button Login").assertTextEquals(stringRes(R.string.sign_up))
        composeTestRule.onNodeWithContentDescription("Tab Sign In").performClick()
        composeTestRule.onNodeWithContentDescription("Tab Sign In").assertTextColor(colorText)
        composeTestRule.onNodeWithContentDescription("Tab Sign Up").assertTextColor(colorText.copy(alpha = 0.3f))
        composeTestRule.onNodeWithContentDescription("TextField Sign In Email").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("TextField Sign In Password").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Button Login").assertTextEquals(stringRes(R.string.sign_in))
    }

    @Test
    fun changeLoginInput() {
        composeTestRule.onNodeWithContentDescription("Button Login").assertIsNotEnabled()
        composeTestRule.onNodeWithContentDescription("TextField Sign In Email").performTextInput(EMAIL)
        composeTestRule.onNodeWithContentDescription("Button Login").assertIsNotEnabled()
        composeTestRule.onNodeWithContentDescription("TextField Sign In Password").performTextInput(PASSWORD)
        composeTestRule.onNodeWithContentDescription("Button Login").assertIsEnabled()
        assertEquals(viewModel.state.email, EMAIL)
        assertEquals(viewModel.state.password, PASSWORD)
    }
}