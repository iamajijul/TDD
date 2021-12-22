package com.ajijul.tdd.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ajijul.tdd.MainCoroutineDispatcherRule
import com.ajijul.tdd.getOrAwaitValueTest
import com.ajijul.tdd.other.Constant
import com.ajijul.tdd.other.Status
import com.ajijul.tdd.repository.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class ShoppingViewModelTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineDispatcherRule  = MainCoroutineDispatcherRule()
    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field return error`() {
        viewModel.insertShoppingItem("Any Thing", "", "")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item name with too long field return error`() {
        val string = buildString {
            for (i in 1..Constant.MAX_NAME_LENGTH + 1)
                append(1)
        }
        viewModel.insertShoppingItem(string, "5", "1.0")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item price with too long field return error`() {
        val string = buildString {
            for (i in 1..Constant.MAX_PRICE_LENGTH + 1)
                append(1)
        }
        viewModel.insertShoppingItem("Name", "5", string)
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount field return error`() {
        viewModel.insertShoppingItem("Name", "555555555555", "1.1")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input field return success`() {
        viewModel.insertShoppingItem("Name", "5", "1.1")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }


}