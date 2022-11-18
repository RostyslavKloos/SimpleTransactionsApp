package ua.rodev.printectestapp.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.rodev.printectestapp.data.cache.MainSettingsDataStore
import ua.rodev.printectestapp.domain.SettingsDataStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(
        @ApplicationContext context: Context,
    ): SettingsDataStore = MainSettingsDataStore(context)
}
