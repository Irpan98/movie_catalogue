package id.itborneo.moviecatalogue.ui.main.activity

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.ui.main.fragment.tvShow.TvShowFragment
import id.itborneo.moviecatalogue.ui.main.fragment.favorite.FavoriteFragment
import id.itborneo.moviecatalogue.ui.main.fragment.movie.MovieFragment
import id.itborneo.moviecatalogue.utils.ConnectivityReceiver
import id.itborneo.moviecatalogue.utils.ShowView


class MainActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {
    
    private var snackBar: Snackbar? = null
    private lateinit var showView: ShowView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showView = ShowView(applicationContext)
        showView.changeAppBar(window)
        
//        registerConnectionReceiver()
        
        val navigation = findViewById<BottomNavigationView>(R.id.bNavigation)
        navigation.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener)
        if (savedInstanceState == null) navigation.selectedItemId = R.id.navigationMovies
        
    }
    
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        snackBar?.let { showView.showNetworkMessage(isConnected, it) }
    }
    
    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    
    override fun onStop() {
        super.onStop()
    }
    
    
    private fun registerConnectionReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val receiver = ConnectivityReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            registerReceiver(receiver, intentFilter)
        }
    }
    
    private val mNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        val fragment: Fragment
        
        when (it.itemId) {
            R.id.navigationMovies -> {
                fragment = MovieFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLayout, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationTvShows -> {
                fragment = TvShowFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLayout, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationFavorite -> {
                fragment = FavoriteFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLayout, fragment, fragment.javaClass.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    
    
}
