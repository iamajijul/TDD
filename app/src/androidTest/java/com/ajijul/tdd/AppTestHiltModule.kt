package com.ajijul.tdd

import android.content.Context
import androidx.room.Room
import com.ajijul.tdd.data.local.ShoppingItemDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppTestHiltModule {
    @Provides
    @Named("test_db")
    fun provideInMemoryDatabase(
        @ApplicationContext applicationContext: Context
    ) = Room.inMemoryDatabaseBuilder(applicationContext, ShoppingItemDB::class.java)
        .allowMainThreadQueries().build()
}