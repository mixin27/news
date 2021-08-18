package com.norm.news.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.norm.news.util.Constants.Companion.DEFAULT_CATEGORY
import com.norm.news.util.Constants.Companion.DEFAULT_SORT_BY
import com.norm.news.util.Constants.Companion.PREFS_BACK_ONLINE
import com.norm.news.util.Constants.Companion.PREFS_CATEGORY
import com.norm.news.util.Constants.Companion.PREFS_CATEGORY_ID
import com.norm.news.util.Constants.Companion.PREFS_NAME
import com.norm.news.util.Constants.Companion.PREFS_SORT_BY
import com.norm.news.util.Constants.Companion.PREFS_SORT_BY_ID
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
        val selectedCategory = stringPreferencesKey(PREFS_CATEGORY)
        val selectedCategoryId = intPreferencesKey(PREFS_CATEGORY_ID)
        val selectedSortBy = stringPreferencesKey(PREFS_SORT_BY)
        val selectedSortById = intPreferencesKey(PREFS_SORT_BY_ID)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    /** save */
    suspend fun saveBackOnline(status: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.backOnline] = status
        }
    }

    suspend fun saveFilterTypes(
        category: String,
        categoryId: Int,
        sortBy: String,
        sortById: Int
    ) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.selectedCategory] = category
            prefs[PreferenceKeys.selectedCategoryId] = categoryId
            prefs[PreferenceKeys.selectedSortBy] = sortBy
            prefs[PreferenceKeys.selectedSortById] = sortById
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

    val readFilterTypes: Flow<FilterTypes> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val selectedCategory = prefs[PreferenceKeys.selectedCategory] ?: DEFAULT_CATEGORY
            val selectedCategoryId = prefs[PreferenceKeys.selectedCategoryId] ?: 0
            val selectedSortBy = prefs[PreferenceKeys.selectedSortBy] ?: DEFAULT_SORT_BY
            val selectedSortById = prefs[PreferenceKeys.selectedSortById] ?: 0
            FilterTypes(
                selectedCategory,
                selectedCategoryId,
                selectedSortBy,
                selectedSortById
            )
        }
}

data class FilterTypes(
    val selectedCategory: String,
    val selectedCategoryId: Int,
    val selectedSortBy: String,
    val selectedSortById: Int
)