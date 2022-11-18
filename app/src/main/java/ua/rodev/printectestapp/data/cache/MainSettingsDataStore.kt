package ua.rodev.printectestapp.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import ua.rodev.printectestapp.BuildConfig
import ua.rodev.printectestapp.data.cache.MainSettingsDataStore.PreferencesKeys.BASE_URL
import ua.rodev.printectestapp.data.cache.MainSettingsDataStore.PreferencesKeys.SALES_MAX_AMOUNT
import ua.rodev.printectestapp.data.cache.MainSettingsDataStore.PreferencesKeys.SALES_MIN_AMOUNT
import ua.rodev.printectestapp.domain.SettingsDataStore

class MainSettingsDataStore(
    context: Context,
) : SettingsDataStore {
    private val Context.getDataStore: DataStore<Preferences> by preferencesDataStore(name = "appDataStore")
    private val dataStore = context.getDataStore

    override suspend fun saveSalesMinAmount(value: Double?) {
        dataStore.edit { preferences -> preferences[SALES_MIN_AMOUNT] = value ?: 0.0 }
    }

    override suspend fun saveSalesMaxAmount(value: Double?) {
        dataStore.edit { preferences -> preferences[SALES_MAX_AMOUNT] = value ?: 0.0 }
    }

    override suspend fun saveHostURL(value: String) {
        dataStore.edit { preferences -> preferences[BASE_URL] = value }
    }

    override suspend fun readSalesMinAmount(): Double? {
        return dataStore.data.first()[SALES_MIN_AMOUNT]
    }

    override suspend fun readSalesMaxAmount(): Double? {
        return dataStore.data.first()[SALES_MAX_AMOUNT]
    }

    override suspend fun readHostURL(): String {
        val url = dataStore.data.first()[BASE_URL]
        return if (url.isNullOrEmpty()) BuildConfig.BASE_URL else url
    }

    private object PreferencesKeys {
        val SALES_MIN_AMOUNT = doublePreferencesKey("salesMinAmount")
        val SALES_MAX_AMOUNT = doublePreferencesKey("salesMaxAmount")
        val BASE_URL = stringPreferencesKey("baseURL")
    }
}
