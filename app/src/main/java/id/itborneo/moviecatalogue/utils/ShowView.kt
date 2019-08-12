package id.itborneo.moviecatalogue.utils

import android.content.Context
import android.os.Build
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.Window
import android.widget.ProgressBar
import id.itborneo.moviecatalogue.ui.main.model.Movie
import id.itborneo.moviecatalogue.MovieTvAdapter
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.activities.DetailMovieTvActivity
import org.jetbrains.anko.intentFor

class ShowView(private var context: Context) {

    fun showLoading(state: Boolean, progressBar: ProgressBar?) {
        if (state) {
            progressBar?.visibility = View.VISIBLE
        }
        else{
            progressBar?.visibility = View.GONE
        }
    }

    fun showSelectedMovie(movie: Movie) {

        context.startActivity(context.intentFor<DetailMovieTvActivity>(
            DetailMovieTvActivity.IS_MOVIE to true,
            DetailMovieTvActivity.MOVIE to movie
        ))
    }


    fun showRecyclerList(movies: ArrayList<Movie>?, recyclerView: RecyclerView?, movieAdapter: MovieTvAdapter) {

    recyclerView?.layoutManager = LinearLayoutManager(context)
    movies?.let { movieAdapter.listMoviesTv(it) }
        recyclerView?.adapter = movieAdapter
        recyclerView?.let {
            ItemClickSupport.addTo(it)
            .setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    movies?.get(position)?.let { it1 -> showSelectedMovie(it1) }
                }
            })
        }
    }

    fun changeAppBar(window: Window){
        if(Build.VERSION.SDK_INT >= 23){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(context, R.color.statusBar)
        }
    }

    fun showNetworkMessage(connected: Boolean, snackBar: Snackbar) {
        if(!connected){
            snackBar.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar.show()
        } else {
            snackBar.dismiss()
        }
    }

}