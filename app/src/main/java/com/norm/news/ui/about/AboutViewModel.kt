package com.norm.news.ui.about

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.norm.news.BuildConfig

/**
 * Created by Kyaw Zayar Tun on 2020-01-18.
 */
class AboutViewModel(
  application: Application
) : ViewModel() {
  val versionCode = BuildConfig.VERSION_CODE
  val versionName = BuildConfig.VERSION_NAME
  val buildType = BuildConfig.BUILD_TYPE
  val buildVersionNumber = "Version $versionName, $buildType $versionCode"
}