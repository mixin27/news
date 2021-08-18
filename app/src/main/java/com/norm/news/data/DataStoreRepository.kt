package com.norm.news.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.norm.news.util.Constants.Companion.PREFS_BACK_ONLINE
import com.norm.news.util.Constants.Companion.PREFS_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/18/21.
 */

// Call it once at the top level of your kotlin file,
// and access it through this property throughout the rest of your application.
// This makes it easier to keep your DataStore as a singleton.
private val Context.dataStore by preferencesDataStore(name = PREFS_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private object PreferenceKeys {
        val backOnline = booleanPreferencesKey(PREFS_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    /** save */
    suspend fun saveBackOnline(status: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.backOnline] = status
        }
    }

    /** read */
    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val backOnline = prefs[PreferenceKeys.backOnline] ?: false
            backOnline
        }

}