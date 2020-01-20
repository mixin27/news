package com.norm.news

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.norm.news.utils.IMMLeaks

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
