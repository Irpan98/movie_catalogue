package id.itborneo.moviecatalogue.utils

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.ui.detail.DetailMovieTvActivity
import id.itborneo.moviecatalogue.ui.adapter.MovieTvAdapter
import id.itborneo.moviecatalogue.ui.model.MovieTv
import org.jetbrains.anko.intentFor

class ShowView(private var context: Context) {
    
    fun showLoading(state: Boolean, progressBar: ProgressBar?) {
        if (state) {
            progressBar?.visibility = View.VISIBLE
        } else {
            progressBar?.visibility = View.GONE
        }
    }
    
    fun showSelectedMovie(movie: MovieTv?, category: String) {
        var isMovie = true
    
        if (category != MOVIE) isMovie = false
    
        context.startActivity(
            context.intentFor<DetailMovieTvActivity>(
                IS_MOVIE to isMovie,
                MOVIE to movie
            )
        )
        
        
    }
    
    fun showRecyclerList(
        movies: ArrayList<MovieTv>?,
        recyclerView: RecyclerView?,
        movieAdapter: MovieTvAdapter,
        category: String
    ) {
        Log.d("showRecyclerList", movies.toString())
        recyclerView?.layoutManager = LinearLayoutManager(context)
        movies?.let { movieAdapter.listMoviesTv(it) }
        recyclerView?.adapter = movieAdapter
        recyclerView?.let {
            ItemClickSupport.addTo(it)
                .setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                    override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                        showSelectedMovie(
                            movies?.get(position), category
                        )
                    }
                })
        }
    }
    
    fun changeAppBar(window: Window) {
        if (Build.VERSION.SDK_INT >= 23) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(context, R.color.statusBar)
        }
    }
    
    fun showNetworkMessage(connected: Boolean, snackBar: Snackbar) {
        if (!connected) {
            snackBar.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar.show()
        } else {
            snackBar.dismiss()
        }
    }
    
    

    
}