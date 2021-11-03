package com.adyen.android.assignment.data.di

import android.content.Context
import androidx.room.Room
import com.adyen.android.assignment.data.cache.Cache
import com.adyen.android.assignment.data.cache.VenueDatabase
import com.adyen.android.assignment.data.cache.model.Converters
import com.adyen.android.assignment.data.util.Constants.DATABASE_NAME
import com.adyen.android.assignment.data.cache.RoomCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {
        @Provides
        @Singleton
        fun provideAnimeDao(db: VenueDatabase) = db.venueDao()

        @Provides
        @Singleton
        fun provideAnimeDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
            app,
            VenueDatabase::class.java,
            DATABASE_NAME
        )   .addTypeConverter(Converters())
            .fallbackToDestructiveMigration()
            .build()
    }
}