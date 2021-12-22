package com.ajijul.tdd.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var shoppingItemDB: ShoppingItemDB

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setUp() {
        hiltRule.inject()
        shoppingDao = shoppingItemDB.getShoppingDao()
    }

    @After
    fun tearDown() {
        shoppingItemDB.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("Orange", 2, 1.5f, "URL", 1)
        shoppingDao.insertShoppingItem(shoppingItem)
        val allShoppingItems = shoppingDao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("Orange", 2, 1.5f, "URL", 1)
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem)
        val allShoppingItems = shoppingDao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPrice() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("Orange 1", 2, 15f, "URL", 1)
        val shoppingItem2 = ShoppingItem("Orange 2", 5, 2.5f, "URL", 2)
        val shoppingItem3 = ShoppingItem("Orange 3", 4, 3.5f, "URL", 3)

        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val totalPrice = shoppingDao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPrice).isEqualTo(2 * 15f + 5 * 2.5f + 4 * 3.5f)
    }
}