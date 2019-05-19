package com.app.simplelogin.ui.main.user

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.app.simplelogin.R
import com.app.simplelogin.ui.main.MainActivity
import com.app.simplelogin.ui.main.user.viewmodel.UserListViewModel
import com.app.simplelogin.utils.DataBindingIdlingResourceRule
import com.app.simplelogin.utils.ViewModelUtil
import com.app.simplelogin.utils.mock
import com.hackernewstopstories.sample.utils.RecyclerHelpers
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class UserListFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private lateinit var viewModel: UserListViewModel

    private val fragment = UserListFragment()

    @Before
    fun init() {
        viewModel = Mockito.mock(UserListViewModel::class.java)
        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
    }

    @Test
    fun loadUsersList() {
        onView(withId(R.id.users_list_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.users_list_rv)).perform(
            RecyclerHelpers.waitUntil(
                RecyclerHelpers.hasItemCount(Matchers.greaterThan(0))))
        onView(withId(R.id.users_list_rv)).check(matches(RecyclerHelpers.hasItemCount(greaterThanOrEqualTo(10))))
    }

    @Test
    fun testItemClick() {
        onView(withId(R.id.users_list_rv)).perform(
            RecyclerHelpers.waitUntil(
                RecyclerHelpers.hasItemCount(Matchers.greaterThan(0))))
        onView(withId(R.id.users_list_rv)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    class TestFragment : UserListFragment() {
        val navController = mock<NavController>()
        override fun navController() = navController
    }
}