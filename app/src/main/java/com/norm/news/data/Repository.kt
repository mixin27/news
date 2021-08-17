package com.norm.news.data

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * Created by Kyaw Zayar Tun on 8/17/21.
 */
@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}