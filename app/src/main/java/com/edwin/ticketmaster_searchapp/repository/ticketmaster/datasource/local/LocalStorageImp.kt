package com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson

class LocalStorageImp(context: Context) : LocalStorage() {

    private val sharedPreferences: SharedPreferences
    private val gson = Gson()

    init {
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            SHARED_PREFERENCES_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun clearData() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }

    override fun putStringData(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getStringData(key: String): String? {
        with(sharedPreferences) {
            return getString(key, null)
        }
    }

    override fun <T> putSerializedData(key: String, value: T) {
        with(sharedPreferences.edit()) {
            val json = gson.toJson(value)
            this.putString(key, json)
            apply()
        }
    }

    override fun <T> getSerializedData(key: String, typeClass: Class<T>): T? {
        with(sharedPreferences) {
            return gson.fromJson(getString(key, null), typeClass)
        }
    }

    companion object {
        private const val SHARED_PREFERENCES_FILE_NAME = "secret_shared_prefs"
    }
}