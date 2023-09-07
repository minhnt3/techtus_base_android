package jp.android.app.features.login

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import jp.android.app.domain.data.AppException
import jp.android.app.domain.data.AppResult
import jp.android.app.domain.entity.User
import jp.android.app.domain.use_case.LoginUseCase
import jp.android.app.test_util.*
import kotlinx.coroutines.flow.lastOrNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LoginViewModelTest {

    @get:Rule
    var rule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var loginUseCase: LoginUseCase

    private lateinit var viewModel: LoginViewModel

    private val user = User()

    @Before
    fun init() {
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase,
        )
        setupUseCase()
    }

    private fun setupUseCase() {
//        coEvery { loginUseCase(EMPTY_STRING, PASSWORD) } throws AppException.Local.ValidationException.EmptyEmail
//        coEvery { loginUseCase(EMAIL_INVALID, PASSWORD) } throws AppException.Local.ValidationException.InvalidEmail
//        coEvery { loginUseCase(EMAIL, EMPTY_STRING) } throws AppException.Local.ValidationException.EmptyPassword
        coEvery { loginUseCase(EMAIL, PASSWORD) } returns AppResult.Success(user)
    }

    @Test
    fun loginEmptyEmail() = runTest {
        viewModel.onEmailOrPasswordChanged(EMPTY_STRING, PASSWORD)
        assertEquals(viewModel.state.email, EMPTY_STRING)
        assertEquals(viewModel.state.password, PASSWORD)
        viewModel.onLoginPressed()
        assertEquals(AppException.Local.ValidationException.EmptyEmail, viewModel.uiState.lastOrNull()?.exception)
    }

    @Test
    fun loginInvalidEmail() = runTest {
        viewModel.onEmailOrPasswordChanged(EMAIL_INVALID, PASSWORD)
        assertEquals(viewModel.state.email, EMAIL_INVALID)
        assertEquals(viewModel.state.password, PASSWORD)
        viewModel.onLoginPressed()
        assertEquals(AppException.Local.ValidationException.InvalidEmail, viewModel.uiState.lastOrNull()?.exception)
    }

    @Test
    fun loginEmptyPassword() = runTest {
        viewModel.onEmailOrPasswordChanged(EMAIL, EMPTY_STRING)
        assertEquals(viewModel.state.email, EMAIL)
        assertEquals(viewModel.state.password, EMPTY_STRING)
        viewModel.onLoginPressed()
        assertEquals(AppException.Local.ValidationException.EmptyPassword, viewModel.uiState.lastOrNull()?.exception)
    }

    @Test
    fun loginSuccess() = runTest {
        viewModel.onEmailOrPasswordChanged(EMAIL, PASSWORD)
        assertEquals(viewModel.state.email, EMAIL)
        assertEquals(viewModel.state.password, PASSWORD)
        viewModel.onLoginPressed()
        assertNull(viewModel.uiState.lastOrNull()?.exception)
        assertEquals(LoginEvent.LoginSuccess(user), viewModel.event.lastOrNull())
    }
}
