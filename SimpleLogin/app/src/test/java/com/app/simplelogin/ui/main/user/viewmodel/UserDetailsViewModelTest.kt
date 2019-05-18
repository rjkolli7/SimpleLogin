package com.app.simplelogin.ui.main.user.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.simplelogin.network.ApiInterface
import com.app.simplelogin.network.model.User
import com.app.simplelogin.ui.di.MockAppModule
import com.app.simplelogin.ui.util.TestUtil
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserDetailsViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockAppModule = MockAppModule()

    private var apiInterface = mock(ApiInterface::class.java)

    private val viewModel = UserDetailsViewModel()

    @Test
    fun loadUsers() {
        apiInterface = mockAppModule.getApiInterface()
        val usersList : MutableList<User> = ArrayList()
        assertThat(usersList.size, equalTo(0))
        apiInterface.users().
            doOnSubscribe {
                println()
            }.subscribe(
            {
                usersList.addAll(it)
                assertThat(true, equalTo(usersList.size > 0))
                val user = usersList[0]
                viewModel.bind(user)
                testUserDetailsAfterApiCall(user)
                testViewModelValues(user)
                println("----------------\n\n\n\nSuccess ${it.size}\n\n\n\n---------------------")
            },
            {
                assertThat(true, equalTo(usersList.size == 0))
                println("----------------\n\n\n\nError ${it.localizedMessage}\n\n\n\n-----------")
            })
    }

    @Test
    fun testMockUser() {
        val mockUser = TestUtil.createMockUser()
        viewModel.bind(mockUser)

        assertThat(viewModel.address.value, notNullValue())
        assertThat(viewModel.company.value, notNullValue())
        assertThat(viewModel.geo.value, notNullValue())

        testUserDetailsAfterApiCall(mockUser)
        testViewModelValues(mockUser)
    }

    @Test
    fun testNullUser() {
        val nullUser = User()
        viewModel.bind(nullUser)

        assertThat(viewModel.address.value, nullValue())
        assertThat(viewModel.company.value, nullValue())
        assertThat(viewModel.geo.value, nullValue())

        assertThat(true, equalTo(viewModel.address.value.isNullOrEmpty()))
        assertThat(true, equalTo(viewModel.company.value.isNullOrEmpty()))
        assertThat(true, equalTo(viewModel.geo.value.isNullOrEmpty()))
    }

    private fun testViewModelValues(user: User) {
        var address = ""
        user.address?.let { a ->
            address = "${a.street}, ${a.suite}, ${a.city}, ${a.zipcode}"
        }
        assertThat(address, equalTo(viewModel.address.value))

        var geo = ""
        user.address?.geo?.let { g ->
            geo = "${g.lat},${g.lng}"
        }
        assertThat(geo, equalTo(viewModel.geo.value))

        var company= ""
        user.company?.let { c ->
            company = "${c.name}\n${c.catchPhrase}\n${c.bs}"
        }
        assertThat(company, equalTo(viewModel.company.value))
    }

    private fun testUserDetailsAfterApiCall(user: User?) {
        assertThat(user, notNullValue())
        assertThat(user?.email, notNullValue())
        assertThat(user?.id, notNullValue())
        assertThat(user?.name, notNullValue())
        assertThat(user?.phone, notNullValue())
        assertThat(user?.website, notNullValue())
        assertThat(user?.address, notNullValue())
        assertThat(user?.address?.city, notNullValue())
        assertThat(user?.address?.street, notNullValue())
        assertThat(user?.address?.suite, notNullValue())
        assertThat(user?.address?.zipcode, notNullValue())
        assertThat(user?.address?.geo, notNullValue())
        assertThat(user?.address?.geo?.lat, notNullValue())
        assertThat(user?.address?.geo?.lng, notNullValue())
        assertThat(user?.company, notNullValue())
        assertThat(user?.company?.name, notNullValue())
        assertThat(user?.company?.bs, notNullValue())
        assertThat(user?.company?.catchPhrase, notNullValue())
    }
}