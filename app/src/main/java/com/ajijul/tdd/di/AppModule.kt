package com.ajijul.tdd.di

import android.content.Context
import androidx.room.Room
import com.ajijul.tdd.data.local.ShoppingDao
import com.ajijul.tdd.data.local.ShoppingItemDB
import com.ajijul.tdd.data.remote.PixabayAPI
import com.ajijul.tdd.other.Constant.BASE_URL
import com.ajijul.tdd.other.Constant.DATABASE_NAME
import com.ajijul.tdd.repository.DefaultShoppingRepository
import com.ajijul.tdd.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDB::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideShoppingItemDao(
        database: ShoppingItemDB
    ) = database.getShoppingDao()

    @Provides
    @Singleton
    fun providePixabayAPI(
    ): PixabayAPI =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
            .build().create(PixabayAPI::class.java)


    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(dao: ShoppingDao, pixabayAPI: PixabayAPI) =
        DefaultShoppingRepository(dao, pixabayAPI) as ShoppingRepository
}









