package com.app.simplelogin.ui.main.user

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.app.simplelogin.R
import com.app.simplelogin.network.model.User
import com.app.simplelogin.ui.main.MainActivity
import com.app.simplelogin.ui.main.user.viewmodel.UserDetailsViewModel
import com.app.simplelogin.utils.DataBindingIdlingResourceRule
import com.app.simplelogin.utils.TestUtil
import com.app.simplelogin.utils.ViewModelUtil
import com.app.simplelogin.utils.mock
import com.hackernewstopstories.sample.utils.RecyclerHelpers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.math.log

@RunWith(AndroidJUnit4::class)
class UserDetailsFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private lateinit var viewModel: UserDetailsViewModel

    private val fragment = TestFragment()
    private val userDetailsFragment = UserDetailsFragment()

    private val user = TestUtil.createMockUser()

    @Before
    fun init() {
        viewModel = Mockito.mock(UserDetailsViewModel::class.java)
        userDetailsFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
    }

    @Test
    fun testDefaultViews() {

        onView(withId(R.id.users_list_rv)).perform(
            RecyclerHelpers.waitUntil(
                RecyclerHelpers.hasItemCount(Matchers.greaterThan(0))
            )
        )
        onView(withId(R.id.users_list_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )
        onView(withId(R.id.ud_name_tv)).check(matches(isDisplayed()))

        onView(withId(R.id.ud_email_tv)).check(matches(isDisplayed()))

        onView(withId(R.id.ud_phone_tv)).check(matches(isDisplayed()))

        onView(withId(R.id.ud_website_tv)).check(matches(isDisplayed()))

        onView(withId(R.id.ud_address_tv)).check(matches(isDisplayed()))

        onView(withId(R.id.ud_company_tv)).check(matches(isDisplayed()))
    }

    @Test
    fun testMockData() {
        onView(withId(R.id.users_list_rv)).perform(
            RecyclerHelpers.waitUntil(
                RecyclerHelpers.hasItemCount(Matchers.greaterThan(0))
            )
        )
        onView(withId(R.id.users_list_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )
        viewModel.bind(user)

        onView(withId(R.id.ud_name_tv)).check(matches(withText("Leanne Graham")))
        onView(withId(R.id.ud_email_tv)).check(matches(withText("Sincere@april.biz")))
        onView(withId(R.id.ud_phone_tv)).check(matches(withText("1-770-736-8031 x56442")))
        onView(withId(R.id.ud_website_tv)).check(matches(withText("hildegard.org")))
        onView(withId(R.id.ud_address_tv)).check(matches(withText("Kulas Light, Apt. 556, Gwenborough, 92998-3874")))
        onView(withId(R.id.ud_company_tv)).check(matches(withText("Romaguera-Crona\nMulti-layered client-server neural-net\nharness real-time e-markets")))
    }

    class TestFragment : UserListFragment() {
        val navController = mock<NavController>()
        override fun navController() = navController
    }
}