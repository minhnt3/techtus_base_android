package jp.android.app.features.login

import androidx.test.filters.SmallTest
import jp.android.app.domain.utils.ValidationUtils
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@SmallTest
class LoginTest {
    @Test
    fun `empty username returns false`() {
        val result = ValidationUtils.validateLoginInput("", "123")
        assertFalse(result)
    }

    @Test
    fun `empty username length less then 6 returns false`() {
        val result = ValidationUtils.validateLoginInput("absdf", "123")
        assertFalse(result)
    }

    @Test
    fun `check email not match pattern return false`() {
        val result = ValidationUtils.validateEmail("_absd@gmail@asc.com")
        assertFalse(result)
    }

    @Test
    fun `check email match with pattern return true`() {
        val result = ValidationUtils.validateEmail("_absd@gmail.com")
        assertTrue(result)
    }
}