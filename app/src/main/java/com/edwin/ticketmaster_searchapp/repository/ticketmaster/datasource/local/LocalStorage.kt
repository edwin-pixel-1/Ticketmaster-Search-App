package com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.local

abstract class LocalStorage {

    abstract fun putStringData(key: String, value: String)

    abstract fun getStringData(key: String): String?


    abstract fun clearData()

    abstract fun <T> putSerializedData(key: String, value: T)

    abstract fun <T> getSerializedData(key: String, typeClass: Class<T>): T?
}