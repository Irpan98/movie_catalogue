package id.itborneo.moviecatalogue.widget2

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.InjectorUtils
import id.itborneo.moviecatalogue.utils.MOVIE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.bundleOf

class FavStackRemoveFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    
    private val movies = mutableListOf<MovieTv>()
    
    private val appDatabase = context.let { InjectorUtils.provideAppDatabase(it) }
    private val favoriteDao = InjectorUtils.provideFavoriteDao(appDatabase)
    private val disposables = CompositeDisposable()
    
    private fun getFavData(){

        disposables.add(
            favoriteDao.getMovieByCategory(MOVIE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { it ->

                    it.forEach {
                        val movie =
                            MovieTv(it.title, it.imageUrl, it.description, it.dateRelease, it.voteAverage, it.id)
                        movies.add(movie)
                    }
                    

                    Log.d("FavStackRemoteFactory","getFavData called: size ${movies.size}")
                }
        )
    }

    
    override fun onDataSetChanged() {
        Log.d("FavStackRemoteFactory","onDatasetChanged Called")
    
        getFavData()

    }
    
    override fun hasStableIds(): Boolean = false
    
    override fun getViewAt(position: Int): RemoteViews {
        Log.d("FavStackRemoteFactory","getView at Called position $position")
    
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        rv.setTextViewText(R.id.textView, movies[position].titleMovieTv)

        val extras = bundleOf(
            FavWidget.EXTRA_ITEM to position
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.textView, fillInIntent)

        return rv

    }
    
    override fun getCount(): Int  {
        Log.d("FavStackRemoteFactory","get Count: size is ${movies.size}")
    
        return movies.size

    
    
    }
    
    override fun getViewTypeCount(): Int = 1
    
    override fun onDestroy() {}
    override fun getItemId(position: Int): Long = 0
    
    
    override fun onCreate() {
        Log.d("FavStackRemoteFactory","onCreate called")
    
    }
    override fun getLoadingView(): RemoteViews? = null
    
    
    
}