package ua.rodev.printectestapp.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.rodev.printectestapp.domain.HandleError
import ua.rodev.printectestapp.presentation.AmountValidation
import ua.rodev.printectestapp.presentation.HandleDomainError
import ua.rodev.printectestapp.presentation.ManageResources
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    @Singleton
    fun provideHandleError(
        manageResources: ManageResources,
    ): HandleError<String> = HandleDomainError(manageResources)

    @Provides
    @Singleton
    fun provideAmountValidation(): AmountValidation = AmountValidation.Main()
}
