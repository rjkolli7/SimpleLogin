package com.app.simplelogin.ui.auth.viewmodel

import androidx.test.core.app.ApplicationProvider
import com.app.simplelogin.App
import com.app.simplelogin.db.Login
import com.app.simplelogin.sharedpref.SessionManager
import com.app.simplelogin.ui.di.MockRoomModule
import com.app.simplelogin.utils.isValidEmail
import com.app.simplelogin.utils.isValidPassword
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoginViewModelTest {

    private val app = ApplicationProvider.getApplicationContext<App>()

    private val mockRoomModule = MockRoomModule()
    private val db = mockRoomModule.provideDb(app)
    private val loginDao = mockRoomModule.provideLoginDao(db)

    private val loginViewModel = LoginViewModel(loginDao)

    private val sessionManager = SessionManager.getInstance(app)

    @Test
    fun testValidation() {
        assertThat(loginViewModel.email, nullValue())
        assertThat(loginViewModel.password, nullValue())
        assertThat(loginViewModel.countryValue.value, nullValue())

        val isValid = loginViewModel.isValid(app)
        assertThat(false, equalTo(isValid))

        assertThat(false, equalTo(isValidPassword("abc@")))
        assertThat(false, equalTo(isValidEmail("sincere.biz")))
        assertThat(true, equalTo(isValidPassword("abc@12345")))
        assertThat(true, equalTo(isValidEmail("sincere@april.biz")))
    }

    @Test
    fun testUser() {
        assertThat(db, notNullValue())
        assertThat(loginDao, notNullValue())
        val login = loginDao.findByEmail("sincere@april.biz") // Expected Login("sincere@april.biz", "abc@12345")
        val invalidLogin = loginDao.findByEmail("sincere@april.bi") // Expected null
        assertThat(login, notNullValue())
        assertThat(invalidLogin, nullValue())
    }

    @Test
    fun testDbInsert() {
        val login = Login("user@test.com", "abc@12345")
        val newValue = loginDao.createLoginIfNotExists(login)
        assertThat(true, equalTo(newValue > 1))
        val existedValue = loginDao.createLoginIfNotExists(login)
        assertThat(true, equalTo(existedValue == newValue))
    }

    @Test
    fun testViewModel() {
        loginViewModel.email = "sincere@april.biz"
        loginViewModel.password = "abc@12345"
        loginViewModel.countryValue.value = "Singapore"

        assertThat(loginViewModel.email, notNullValue())
        assertThat(loginViewModel.password, notNullValue())
        assertThat(loginViewModel.countryValue.value, notNullValue())

        val isValid = loginViewModel.isValid(app)
        assertThat(true, equalTo(isValid))
    }

    @Test
    fun testLogin() {
        loginViewModel.email = "sincere@april.biz"
        loginViewModel.password = "abc@12345"
        loginViewModel.doLogin(app, true)
        assertThat(true, equalTo(sessionManager.isLogin))
    }
}