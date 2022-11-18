package ua.rodev.printectestapp.domain

interface SettingsDataStore {
    suspend fun saveSalesMinAmount(value: Double?)
    suspend fun saveSalesMaxAmount(value: Double?)
    suspend fun saveHostURL(value: String)
    suspend fun readSalesMinAmount(): Double?
    suspend fun readSalesMaxAmount(): Double?
    suspend fun readHostURL(): String
}
