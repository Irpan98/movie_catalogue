package id.itborneo.moviecatalogue.ui.main.fragment.favorite.childFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.data.db.AppDatabase
import id.itborneo.moviecatalogue.ui.adapter.MovieTvAdapter
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.InjectorUtils
import id.itborneo.moviecatalogue.utils.MOVIE
import id.itborneo.moviecatalogue.utils.ShowView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieFavoriteFragment : Fragment() {
    
    private lateinit var disposables: CompositeDisposable
    private lateinit var movies: ArrayList<MovieTv>
    private lateinit var favoriteAdapter: MovieTvAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragView = inflater.inflate(R.layout.fragment_movie_favorite, container, false)
        movies = ArrayList()
        getFavorite()
        val recViewMovie = fragView.findViewById<RecyclerView>(R.id.recyMovie)
        
        favoriteAdapter = MovieTvAdapter(context as Context)
        
        ShowView(context as Context)
            .showRecyclerList(
                movies,
                recViewMovie,
                favoriteAdapter,
                MOVIE
            )
        favoriteAdapter.setData(movies)
        return fragView
    }
    
    private fun getFavorite() {
        
        val appDatabase = context?.let { InjectorUtils.provideAppDatabase(it) }
        val favoriteDao = InjectorUtils.provideFavoriteDao(appDatabase as AppDatabase)
        
        disposables = CompositeDisposable()
        disposables.add(
            favoriteDao.getMovieByCategory(MOVIE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { it ->
                    movies.clear()
                    it.forEach {
                        val movie =
                            MovieTv(it.title, it.imageUrl, it.description, it.dateRelease, it.voteAverage, it.id)
                        Log.d("MovieFavoriteFragment", "${movie.imageMovieTv}")
                        movies.add(movie)
                    }
                    favoriteAdapter.notifyDataSetChanged()
                }
        )
    }
    
    override fun onResume() {
        super.onResume()
        getFavorite()
    }
    
}
