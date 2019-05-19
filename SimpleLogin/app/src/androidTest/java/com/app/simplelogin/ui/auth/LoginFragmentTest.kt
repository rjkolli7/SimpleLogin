package com.app.simplelogin.ui.auth

import android.view.KeyEvent
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.app.simplelogin.R
import com.app.simplelogin.sharedpref.SessionManager
import com.app.simplelogin.ui.auth.viewmodel.LoginViewModel
import com.app.simplelogin.utils.DataBindingIdlingResourceRule
import com.app.simplelogin.utils.ViewModelUtil
import com.app.simplelogin.utils.mock
import org.hamcrest.CoreMatchers.anything
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest{

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(AuthActivity::class.java, true, true)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private lateinit var viewModel: LoginViewModel
    private val loginFragment = LoginFragment()

    @Before
    fun init() {
        SessionManager.getInstance(activityRule.activity).saveLogin(false)
        viewModel = Mockito.mock(LoginViewModel::class.java)
        loginFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
    }

    @Test
    fun testDefaultViewState() {
        onView(withId(R.id.login_email_edt)).check(matches(isDisplayed()))
        onView(withId(R.id.login_pwd_edt)).check(matches(isDisplayed()))
        onView(withId(R.id.login_country_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.login_button)).check(matches(isDisplayed()))

        onView(withId(R.id.login_email_edt)).check(matches(withText("")))
        onView(withId(R.id.login_pwd_edt)).check(matches(withText("")))
        onView(withId(R.id.login_country_tv)).check(matches(withText("")))
        onView(withId(R.id.login_button)).check(matches(withText(R.string.login)))

        onView(withId(R.id.login_email_edt)).check(matches(withHint(R.string.enter_email)))
        onView(withId(R.id.login_pwd_edt)).check(matches(withHint(R.string.password_hint)))
        onView(withId(R.id.login_country_tv)).check(matches(withHint(R.string.select_country)))
    }

    @Test
    fun testCountrySelection() {
        onView(withId(R.id.login_country_tv)).perform(click())
        onView(withText(R.string.select_country)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(0).perform(click())
        val statesList = activityRule.activity?.resources?.getStringArray(R.array.countries)
        `when`(viewModel.countryValue).thenReturn(MutableLiveData())
        viewModel.countryValue.postValue(statesList?.get(0))
        onView(withId(R.id.login_country_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.login_country_tv)).check(matches(withText(statesList?.get(0))))
    }

    @Test
    fun testWrongEmailPassword() {
        viewModel.isTesting = true
        onView(withId(R.id.login_email_edt)).perform(typeText("fopopo"), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.login_pwd_edt)).perform(typeText("12345"), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.login_button)).perform(closeSoftKeyboard())
        onView(withId(R.id.login_button)).perform(click())
    }

    @Test
    fun testSuccessLogin() {
        viewModel.isTesting = true
                                                                                      onView(withId(R.id.login_email_edt)).perform(typeText("sincere@april.biz"), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.login_pwd_edt)).perform(typeText("abc@12345"), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.login_button)).perform(closeSoftKeyboard())
        onView(withId(R.id.login_country_tv)).perform(click())
        onView(withText(R.string.select_country)).inRoot(isDialog()).check(matches(isDisplayed()))
        onData(anything()).atPosition(0).perform(click())
        val statesList = activityRule.activity?.resources?.getStringArray(R.array.countries)
        `when`(viewModel.countryValue).thenReturn(MutableLiveData())
        viewModel.countryValue.postValue(statesList?.get(0))
        onView(withId(R.id.login_button)).perform(click())
    }

    class TestFragment : LoginFragment() {
        val navController = mock<NavController>()
        override fun navController() = navController
    }
}