package com.norm.news.ui.about

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Kyaw Zayar Tun on 2020-01-18.
 */
class AboutViewModel(
  application: Application
) : ViewModel() {

  private val _navigateToLicensePage = MutableLiveData<Int>()
  val navigateToLicensePage: LiveData<Int>
    get() = _navigateToLicensePage

  fun displayLicensePage() {
    _navigateToLicensePage.value = 1
  }

  fun displayLicensePageComplete() {
    _navigateToLicensePage.value = 0
  }
}