package com.app.simplelogin.ui.main.user.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.simplelogin.network.ApiInterface
import com.app.simplelogin.ui.di.MockAppModule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockAppModule = MockAppModule()

    private var apiInterface = mock(ApiInterface::class.java)

    private val viewModel = UserListViewModel(apiInterface)

    @Test
    fun loadUsers() {
        apiInterface = mockAppModule.getApiInterface()
        assertThat(viewModel.usersList.size, equalTo(0))
        apiInterface.users().
            doOnSubscribe {
                println()
            }.subscribe(
            {
                viewModel.usersList.addAll(it)
                assertThat(true, equalTo(viewModel.usersList.size > 0))
                println("----------------\n\n\n\nSuccess ${it.size}\n\n\n\n---------------------")
            },
            {
                assertThat(true, equalTo(viewModel.usersList.size == 0))
                println("----------------\n\n\n\nError ${it.localizedMessage}\n\n\n\n-----------")
            })
        println("----------------\n\n\n\nAfter Subscription\n\n\n\n---------------------")
    }
}