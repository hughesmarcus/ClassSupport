package com.supporter.marcus.classsupport

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.supporter.marcus.classsupport.ui.search.SearchFilterViewModel
import kotlinx.android.synthetic.main.fragment_web_view.*
import kotlinx.android.synthetic.main.navigation_activity.*
import org.koin.android.architecture.ext.viewModel


/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
class MainActivity : AppCompatActivity() {
    private var drawerLayout: DrawerLayout? = null
    private lateinit var searchView: MaterialSearchView
    private val filterViewModel by viewModel<SearchFilterViewModel>()
    lateinit var toolbar: Toolbar
    val navController = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar

        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return


        // Set up Action Bar
        val navController = host.navController

        //   setupActionBar(navController)
        setupBottomNavMenu(navController)

        navController.addOnNavigatedListener { _, destination ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            //Toast.makeText(this@MainActivity, "Navigated to $dest",
            //    Toast.LENGTH_SHORT).show()

        }
    }

    private fun setupActionBar(navController: NavController) {

        NavigationUI.setupActionBarWithNavController(this, navController)
    }
    private fun setupBottomNavMenu(navController: NavController) {
        findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.let { bottomNavView ->
            NavigationUI.setupWithNavController(bottomNavView, navController)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return NavigationUI.onNavDestinationSelected(item,
                Navigation.findNavController(this, R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawerLayout,
                Navigation.findNavController(this, R.id.my_nav_host_fragment))
    }

    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun onBackPressed() {

        return when {
            search_view.isSearchOpen -> search_view.closeSearch()
            detial_webview != null && detial_webview.canGoBack() -> {
                detial_webview.goBack()
            }
            else -> super.onBackPressed()
        }
    }

}