package com.dicoding.dicodingevent.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.dicodingevent.core.data.local.FavoriteEventDao
import com.dicoding.dicodingevent.core.data.local.FavoriteEventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoriteEventDatabase =
        Room.databaseBuilder(
            context,
            FavoriteEventDatabase::class.java, "event.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideEventDao(db: FavoriteEventDatabase): FavoriteEventDao = db.favoriteEventDao()
}
