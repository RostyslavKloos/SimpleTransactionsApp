package ua.rodev.printectestapp.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.rodev.printectestapp.presentation.NavigationDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ActivityModule {

    @Provides
    @Singleton
    fun provideNavigationDispatcher(): NavigationDispatcher = NavigationDispatcher.Main()

}
