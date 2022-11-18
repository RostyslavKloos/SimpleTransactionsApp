package ua.rodev.printectestapp.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.rodev.printectestapp.presentation.ManageResources
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object CoreModule {

    @Singleton
    @Provides
    fun provideManageResources(
        @ApplicationContext context: Context,
    ): ManageResources = ManageResources.Main(context)
}