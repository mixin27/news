package com.norm.news

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.norm.news.databinding.ActivityMainBinding
import com.norm.news.utils.IMMLeaks

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    // Request that our window is laid out full screen, to draw edge-to-edge. See the following
    // blog post for more info:
    // https://medium.com/androiddevelopers/gesture-navigation-going-edge-to-edge-812f62e4e83e
    binding.root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

    // for input method memory leaks.
    IMMLeaks.fixFocusedViewLeak(application)

    val navController = this.findNavController(R.id.myNavHostFragment)
    NavigationUI.setupActionBarWithNavController(this, navController)

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val navController = this.findNavController(R.id.myNavHostFragment)
    return NavigationUI.onNavDestinationSelected(
        item,
        navController
    ) || super.onOptionsItemSelected(item)
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = this.findNavController(R.id.myNavHostFragment)
    return navController.navigateUp()
  }
}
